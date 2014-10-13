package org.cytoscape.d3;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.cytoscape.d3.internal.CyActivator;
import org.cytoscape.io.util.StreamUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest(CyActivator.class)
public class ActivatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testActivator() throws Exception {
		CyActivator activator = new CyActivator();
		BundleContext bc = mock(BundleContext.class);
		StreamUtil sUtil = mock(StreamUtil.class);
		ServiceReference ref = mock(ServiceReference.class);
		PowerMockito.when(bc.getServiceReference(StreamUtil.class.getName())).thenReturn(ref);
		PowerMockito.when(activator, "getService", bc, StreamUtil.class).thenReturn(sUtil);
		activator.start(bc);
	}

}
