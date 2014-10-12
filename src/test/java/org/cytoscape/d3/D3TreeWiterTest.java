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
import java.util.HashMap;
import java.util.Map;

import org.cytoscape.d3.internal.serializer.D3TreeModule;
import org.cytoscape.d3.internal.writer.D3NetworkViewWriter;
import org.cytoscape.ding.NetworkViewTestSupport;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class D3TreeWiterTest {

	private final NetworkViewTestSupport support = new NetworkViewTestSupport();

	protected TaskMonitor tm;

	protected Map<Long, CyNode> suid2nodeMap;
	protected Map<Long, CyEdge> suid2edgeMap;

	protected CyNetworkView view;
	private CyNode root;

	@Before
	public void setUp() throws Exception {
		this.tm = mock(TaskMonitor.class);
		suid2nodeMap = new HashMap<Long, CyNode>();
		suid2edgeMap = new HashMap<Long, CyEdge>();

		this.view = generateTreeView();
		assertNotNull(view);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Create a simple tree for testing
	 * 
	 * @return
	 * @throws Exception
	 */
	private CyNetworkView generateTreeView() throws Exception {
		final CyNetwork tree = support.getNetwork();

		root = tree.addNode();

		// Level 1
		CyNode c1 = tree.addNode();
		CyNode c2 = tree.addNode();

		// Level 2
		CyNode c3 = tree.addNode();
		CyNode c4 = tree.addNode();
		CyNode c5 = tree.addNode();

		// Level 3
		CyNode c6 = tree.addNode();
		CyNode c7 = tree.addNode();

		suid2nodeMap.put(root.getSUID(), root);
		suid2nodeMap.put(c1.getSUID(), c1);
		suid2nodeMap.put(c2.getSUID(), c2);
		suid2nodeMap.put(c3.getSUID(), c3);
		suid2nodeMap.put(c4.getSUID(), c4);
		suid2nodeMap.put(c5.getSUID(), c5);
		suid2nodeMap.put(c6.getSUID(), c6);
		suid2nodeMap.put(c7.getSUID(), c7);

		CyEdge e1 = tree.addEdge(root, c1, true);
		CyEdge e2 = tree.addEdge(root, c2, true);

		CyEdge e3 = tree.addEdge(c1, c3, true);
		CyEdge e4 = tree.addEdge(c1, c4, true);
		CyEdge e5 = tree.addEdge(c2, c5, true);

		CyEdge e6 = tree.addEdge(c3, c6, true);
		CyEdge e7 = tree.addEdge(c3, c7, true);

		suid2edgeMap.put(e1.getSUID(), e1);
		suid2edgeMap.put(e2.getSUID(), e2);
		suid2edgeMap.put(e3.getSUID(), e3);
		suid2edgeMap.put(e4.getSUID(), e4);
		suid2edgeMap.put(e5.getSUID(), e5);
		suid2edgeMap.put(e6.getSUID(), e6);
		suid2edgeMap.put(e7.getSUID(), e7);

		tree.getRow(root).set(CyNetwork.NAME, "root");

		tree.getRow(c1).set(CyNetwork.NAME, "c1");
		tree.getRow(c2).set(CyNetwork.NAME, "c2");
		tree.getRow(c3).set(CyNetwork.NAME, "c3");
		tree.getRow(c4).set(CyNetwork.NAME, "c4");
		tree.getRow(c5).set(CyNetwork.NAME, "c5");
		tree.getRow(c6).set(CyNetwork.NAME, "c6");
		tree.getRow(c7).set(CyNetwork.NAME, "c7");

		final CyNetworkView view = support.getNetworkViewFactory().createNetworkView(tree);

		assertEquals(8, view.getModel().getNodeCount());
		assertEquals(7, view.getModel().getEdgeCount());

		return view;
	}

	private final File readTree(final String outFileName) throws Exception {
		final ObjectMapper treeMapper = new ObjectMapper();
		treeMapper.registerModule(new D3TreeModule());

		File temp = new File(outFileName);
		OutputStream os = new FileOutputStream(temp);
		final D3NetworkViewWriter writer = new D3NetworkViewWriter(os, view, treeMapper);
		writer.run(tm);
		os.close();
		return temp;
	}

	@Test(expected = JsonMappingException.class)
	public void testNoRoot() throws Exception {
		readTree("target/d3NoRoot.json");
	}

	@Test
	public void testTree() throws Exception {
		view.getModel().getRow(root).set(CyNetwork.SELECTED, true);
		File temp = readTree("target/d3Tree.json");
		readAndTest(temp, view.getModel());
	}

	private void readAndTest(final File outFile, CyNetwork network) throws IOException {
		final FileInputStream fileInputStream = new FileInputStream(outFile);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode rootNode = mapper.readValue(reader, JsonNode.class);

		assertNotNull(rootNode);
		assertEquals(4, rootNode.size());

		final JsonNode rootName = rootNode.get(CyNetwork.NAME);
		final JsonNode rootSelected = rootNode.get(CyNetwork.SELECTED);
		final JsonNode rootSUID = rootNode.get(CyNetwork.SUID);
		final JsonNode children = rootNode.get("children");
		assertNotNull(rootName);
		assertEquals(true, rootSelected.asBoolean());
		assertNotNull(rootSUID.asLong());
		assertTrue(children.isArray());
	}
}
