package org.cytoscape.d3.internal.writer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncodingUtil.class);
	private static final String ENCODING = "UTF-8";

	public static CharsetEncoder getEncoder() {
		if (Charset.isSupported(ENCODING)) {
			// UTF-8 is supported by system
			return Charset.forName(ENCODING).newEncoder();
		} else {
			// Use default.
			logger.warn("UTF-8 is not supported by this system.  This can be a problem for non-Roman annotations.");
			return Charset.defaultCharset().newEncoder();
		}
	}
}