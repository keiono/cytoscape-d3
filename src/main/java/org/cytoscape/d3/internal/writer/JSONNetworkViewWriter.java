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
public final class JSONNetworkViewWriter extends AbstractNetworkViewTask implements CyWriter {
	
	private static final String ENCODING = "UTF-8";
	
	private static final Logger logger = LoggerFactory.getLogger(JSONNetworkWriter.class);

	final OutputStream outputStream;
	
	protected final CharsetEncoder encoder;
	protected final ObjectMapper networkView2jsonMapper;

	public JSONNetworkViewWriter(final OutputStream outputStream, final CyNetworkView networkView,
			final ObjectMapper networkView2jsonMapper) {
		super(networkView);

		if(outputStream == null)
			throw new NullPointerException("Output Stream is null.");
		
		if(networkView2jsonMapper == null)
			throw new NullPointerException("Object Mapper is null.");
		
		this.outputStream = outputStream;
		this.networkView2jsonMapper = networkView2jsonMapper;
		
		if(Charset.isSupported(ENCODING)) {
			// UTF-8 is supported by system
			this.encoder = Charset.forName(ENCODING).newEncoder();
		} else {
			// Use default.
			logger.warn("UTF-8 is not supported by this system.  This can be a problem for non-Roman annotations.");
			this.encoder = Charset.defaultCharset().newEncoder();
		}
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (taskMonitor != null) {
			taskMonitor.setTitle("Writing Network View to JSON...");
			taskMonitor.setProgress(0);
		}
		networkView2jsonMapper.writeValue(new OutputStreamWriter(outputStream, encoder), view);
		outputStream.close();
	}
}