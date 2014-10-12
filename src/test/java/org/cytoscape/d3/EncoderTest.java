package org.cytoscape.d3;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.cytoscape.d3.internal.writer.EncodingUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EncoderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CharsetEncoder encoder = EncodingUtil.getEncoder();
	
		final Charset charset = encoder.charset();
		assertEquals("UTF-8", charset.name());
	}

}
