package org.cytoscape.d3.internal.writer;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.cytoscape.io.write.CyWriter;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Writer for all JSON format. Output format will be determined by ObjectMapper.
 * 
 */
public final class D3NetworkViewWriter extends AbstractNetworkViewTask implements CyWriter {

	private final OutputStream outputStream;
	private final ObjectMapper networkView2jsonMapper;

	public D3NetworkViewWriter(final OutputStream outputStream, final CyNetworkView networkView,
			final ObjectMapper networkView2jsonMapper) {
		super(networkView);

		if(outputStream == null)
			throw new NullPointerException("Output Stream is null.");
		
		if(networkView2jsonMapper == null)
			throw new NullPointerException("Object Mapper is null.");
		
		this.outputStream = outputStream;
		this.networkView2jsonMapper = networkView2jsonMapper;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (taskMonitor != null) {
			taskMonitor.setTitle("Writing Network View to D3.js Style JSON...");
			taskMonitor.setProgress(0);
		}
		networkView2jsonMapper.writeValue(new OutputStreamWriter(outputStream, EncodingUtil.getEncoder()), view);
		outputStream.close();
		
		if (taskMonitor != null) {
			taskMonitor.setStatusMessage("Success.");
			taskMonitor.setProgress(1.0);
		}
	}
}