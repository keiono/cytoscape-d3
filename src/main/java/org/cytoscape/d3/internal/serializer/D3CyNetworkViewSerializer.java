package org.cytoscape.d3.internal.serializer;

import java.io.IOException;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class D3CyNetworkViewSerializer extends JsonSerializer<CyNetworkView> {


	@Override
	public void serialize(final CyNetworkView networkView, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		final D3JsonBuilder builder = new D3JsonBuilder();
		final CyNetwork network = networkView.getModel();
		builder.serializeNetwork(network, jgen, provider);
	}


	@Override
	public Class<CyNetworkView> handledType() {
		return CyNetworkView.class;
	}
}
