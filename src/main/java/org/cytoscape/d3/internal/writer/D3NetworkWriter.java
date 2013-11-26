package org.cytoscape.d3.internal.writer;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.cytoscape.io.write.CyWriter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.AbstractNetworkTask;
import org.cytoscape.work.TaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Writer for all D3 format. Output format will be determined by ObjectMapper.
 * 
 */
public class D3NetworkWriter extends AbstractNetworkTask implements CyWriter {

	private final OutputStream outputStream;
	private final ObjectMapper network2jsonMapper;

	public D3NetworkWriter(final OutputStream outputStream, final CyNetwork network,
			final ObjectMapper network2jsonMapper) {
		super(network);

		this.outputStream = outputStream;
		this.network2jsonMapper = network2jsonMapper;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (taskMonitor != null) {
			taskMonitor.setTitle("Writing to JSON...");
			taskMonitor.setProgress(0);
		}
		network2jsonMapper.writeValue(outputStream, network);
		outputStream.close();
	}

}
