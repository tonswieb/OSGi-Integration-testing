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
		ServiceReference[] serviceReferences = bundleContext.getBundle(20).getRegisteredServices();
		for (ServiceReference serviceReference : serviceReferences) {
			System.out.println(serviceReference.getClass().getName());
		}
		ServiceReference serviceReference = OsgiServiceReferenceUtils.getServiceReference(bundleContext,new String[] {HelloWorld.class.getName()});
//		ServiceReference serviceReference = bundleContext.getServiceReference(HelloWorld.class.getName());
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
//				 "org.apache.felix, org.apache.felix.configadmin, 1.2.4",
//				 "org.apache.aries.blueprint, org.apache.aries.blueprint, 0.2-incubating",
//				 "org.springframework.osgi, spring-osgi-core, 2.0.0.M1",
				 "com.finalist.test.osgi, integratie-test-spring-dm, 0.0.1-SNAPSHOT"
		 };
	}
	
//	@Override
//	protected Manifest getManifest() {
//		Manifest mf = super.getManifest();
//		String importPackage = mf.getMainAttributes().getValue(Constants.IMPORT_PACKAGE);
//		importPackage = importPackage + ",org.springframework.osgi.blueprint.container";
//		mf.getMainAttributes().putValue(Constants.IMPORT_PACKAGE, importPackage);
//		return mf;
//	}
	
	
}
