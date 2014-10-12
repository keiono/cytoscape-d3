package org.cytoscape.d3.internal.writer;

import java.io.OutputStream;

import org.cytoscape.io.CyFileFilter;
import org.cytoscape.io.write.CyNetworkViewWriterFactory;
import org.cytoscape.io.write.CyWriter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Create Writers for given data type (View or Network)
 * 
 */
public class D3NetworkWriterFactory implements CyNetworkViewWriterFactory {
	
	private final CyFileFilter filter;
	private final ObjectMapper mapper;

	public D3NetworkWriterFactory(final CyFileFilter filter, final ObjectMapper mapper) {
		this.filter = filter;
		this.mapper = mapper;
	}

	@Override
	public CyWriter createWriter(OutputStream outputStream, CyNetworkView view) {
		return new D3NetworkViewWriter(outputStream, view, mapper);
	}

	@Override
	public CyWriter createWriter(OutputStream outputStream, CyNetwork network) {
		return new D3NetworkWriter(outputStream, network, mapper);
	}

	@Override
	public CyFileFilter getFileFilter() {
		return filter;
	}
}