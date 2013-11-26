package org.cytoscape.d3.internal.serializer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class D3TreeModule extends SimpleModule {

	private static final long serialVersionUID = -5676652383674219510L;

	public D3TreeModule() {
		super("D3TreeModule", new Version(1, 0, 0, null, null, null));
		addSerializer(new D3CyNetworkViewTreeSerializer());
		addSerializer(new D3RowSerializer());
	}

}
