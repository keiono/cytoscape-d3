package org.cytoscape.d3;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.cytoscape.d3.internal.writer.D3NetworkViewWriter;
import org.cytoscape.d3.internal.writer.D3NetworkWriter;
import org.cytoscape.d3.internal.writer.D3NetworkWriterFactory;
import org.cytoscape.ding.NetworkViewTestSupport;
import org.cytoscape.io.BasicCyFileFilter;
import org.cytoscape.io.DataCategory;
import org.cytoscape.io.util.StreamUtil;
import org.cytoscape.io.write.CyWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class D3NetworkWriterFactoryTest {

	private final NetworkViewTestSupport support = new NetworkViewTestSupport();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFactory() throws Exception {
		final StreamUtil streamUtil = mock(StreamUtil.class);
		final BasicCyFileFilter filter = new BasicCyFileFilter(new String[] { "json" },
				new String[] { "application/json" }, "Factory test", DataCategory.NETWORK, streamUtil);
		ObjectMapper mapper = new ObjectMapper();
		final D3NetworkWriterFactory factory = new D3NetworkWriterFactory(filter, mapper);

		File temp = new File("target/test1");
		OutputStream os = new FileOutputStream(temp);
		final CyWriter netWriter = factory.createWriter(os, support.getNetwork());
		assertNotNull(netWriter);
		assertTrue(netWriter instanceof D3NetworkWriter);

		final CyWriter netViewWriter = factory.createWriter(os, support.getNetworkView());
		assertNotNull(netViewWriter);
		assertTrue(netViewWriter instanceof D3NetworkViewWriter);
	}

}
