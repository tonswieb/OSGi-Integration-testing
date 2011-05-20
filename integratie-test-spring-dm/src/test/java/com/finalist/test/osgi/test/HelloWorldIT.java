package com.finalist.test.osgi.test;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.util.OsgiServiceReferenceUtils;

import com.finalist.test.osgi.HelloWorld;

public class HelloWorldIT extends AbstractConfigurableBundleCreatorTests {
	
	public void testPrintBundles() {
		System.out.println(bundleContext.getProperty(Constants.FRAMEWORK_VENDOR));
		System.out.println(bundleContext.getProperty(Constants.FRAMEWORK_VERSION));
		System.out.println(bundleContext.getProperty(Constants.FRAMEWORK_EXECUTIONENVIRONMENT));
		
		for (Bundle bundle : bundleContext.getBundles()) {
			System.out.print(bundle.getBundleId());
			System.out.print(" " + bundle.getState());
			System.out.print(" " + bundle.getSymbolicName());
			System.out.println(" " + bundle.getVersion());
		}
	}
	
	public void testBundleActive() {
		ServiceReference serviceReference = bundleContext.getServiceReference(HelloWorld.class.getName());
		assertNotNull(serviceReference);		
		assertEquals(Bundle.ACTIVE,serviceReference.getBundle().getState());
	}

	public void testPrintHelloWorld() {
		ServiceReference serviceReference = bundleContext.getServiceReference(HelloWorld.class.getName());
		Object service = bundleContext.getService(serviceReference);
		assertTrue(service instanceof HelloWorld);
		
		HelloWorld helloWorld = (HelloWorld) service;
		assertEquals("Hello World",helloWorld.print());
	}
	
	@Override
	protected String[] getTestBundlesNames() {
		 return new String[] { 
				 "com.finalist.test.osgi, integratie-test-spring-dm, 0.0.1-SNAPSHOT"
		 };
	}
}