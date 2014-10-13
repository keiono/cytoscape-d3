package org.cytoscape.d3;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cytoscape.d3.internal.serializer.D3jsModule;
import org.cytoscape.d3.internal.writer.D3NetworkViewWriter;
import org.cytoscape.d3.internal.writer.D3NetworkWriter;
import org.cytoscape.ding.NetworkViewTestSupport;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class D3WriterTest {
	
	private final NetworkViewTestSupport support = new NetworkViewTestSupport();

	protected TaskMonitor tm;

	protected Map<Long, CyNode> suid2nodeMap;
	protected Map<Long, CyEdge> suid2edgeMap;
	
	protected CyNetworkView view;

	@Before
	public void setUp() throws Exception {
		this.tm = mock(TaskMonitor.class);
		suid2nodeMap = new HashMap<Long, CyNode>();
		suid2edgeMap = new HashMap<Long, CyEdge>();
		
		this.view = generateNetworkView();
		assertNotNull(view);
	}


	@After
	public void tearDown() throws Exception {
	}

	protected CyNetworkView generateNetworkView() throws Exception {
		final CyNetwork network1 = support.getNetwork();
		CyNode n1 = network1.addNode();
		CyNode n2 = network1.addNode();
		CyNode n3 = network1.addNode();

		// Not connected
		CyNode n4 = network1.addNode();
		CyNode n5 = network1.addNode();

		suid2nodeMap.put(n1.getSUID(), n1);
		suid2nodeMap.put(n2.getSUID(), n2);
		suid2nodeMap.put(n3.getSUID(), n3);
		suid2nodeMap.put(n4.getSUID(), n4);
		suid2nodeMap.put(n5.getSUID(), n5);

		CyEdge e1 = network1.addEdge(n1, n2, true);
		CyEdge e2 = network1.addEdge(n2, n3, true);
		CyEdge e3 = network1.addEdge(n1, n3, true);
		CyEdge e1self = network1.addEdge(n1, n1, true);

		suid2edgeMap.put(e1.getSUID(), e1);
		suid2edgeMap.put(e2.getSUID(), e2);
		suid2edgeMap.put(e3.getSUID(), e3);
		suid2edgeMap.put(e1self.getSUID(), e1self);

		network1.getRow(n1).set(CyNetwork.NAME, "n1");
		network1.getRow(n2).set(CyNetwork.NAME, "n2: 日本語テスト");
		network1.getRow(n3).set(CyNetwork.NAME, "n3");
		network1.getRow(n4).set(CyNetwork.NAME, "n4: Alone");
		network1.getRow(n5).set(CyNetwork.NAME, "n5");
		
		network1.getDefaultNodeTable().createColumn("DoubleTest", Double.class, false);
		network1.getDefaultNodeTable().createColumn("IntTest", Integer.class, false);
		network1.getDefaultNodeTable().createListColumn("StringListTest", String.class, false);
		network1.getRow(n1).set("DoubleTest", 10.2d);
		network1.getRow(n1).set("IntTest", 3);
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		network1.getRow(n1).set("StringListTest", list);

		network1.getRow(e1).set(CyNetwork.NAME, "e1");
		network1.getRow(e2).set(CyNetwork.NAME, "エッジ2");
		network1.getRow(e3).set(CyNetwork.NAME, "e3");
		network1.getRow(e1self).set(CyNetwork.NAME, "e1self");

		final CyNetworkView view = support.getNetworkViewFactory().createNetworkView(network1);
		
		// Create some visual properties
		final View<CyNode> view1 = view.getNodeView(n1);
		view1.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, 50d);
		view1.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, 50d);
		
		assertEquals(5, view.getModel().getNodeCount());
		assertEquals(4, view.getModel().getEdgeCount());

		return view;
	}
	
	@Test
	public void testViewWriter() throws Exception {
		final ObjectMapper jsMapper = new ObjectMapper();
		jsMapper.registerModule(new D3jsModule());

		File temp = new File("target/d3Network1.json");
		OutputStream os = new FileOutputStream(temp);
		final D3NetworkViewWriter writer = new D3NetworkViewWriter(os, view, jsMapper);
		writer.run(tm);

		readAndTest(temp, view.getModel());
		os.close();
		
	}
	
	@Test
	public void testWriter() throws Exception {
		final ObjectMapper jsMapper = new ObjectMapper();
		jsMapper.registerModule(new D3jsModule());

		File temp = new File("target/d3Network1.json");
		OutputStream os = new FileOutputStream(temp);
		final D3NetworkWriter writer = new D3NetworkWriter(os, view.getModel(), jsMapper);
		writer.run(tm);

		readAndTest(temp, view.getModel());
		os.close();
	}


	private void readAndTest(final File outFile, CyNetwork network) throws IOException {
		final FileInputStream fileInputStream = new FileInputStream(outFile);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode rootNode = mapper.readValue(reader, JsonNode.class);

		assertNotNull(rootNode);

		final JsonNode nodes = rootNode.get("nodes");
		final JsonNode edges = rootNode.get("links");
		assertNotNull(nodes);
		assertTrue(nodes.isArray());
		assertNotNull(edges);
		assertTrue(edges.isArray());
		
		assertEquals(5, nodes.size());
		assertEquals(4, edges.size());
		
	}
}