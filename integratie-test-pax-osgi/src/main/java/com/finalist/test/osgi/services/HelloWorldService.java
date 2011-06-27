package com.finalist.test.osgi.services;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.finalist.test.osgi.HelloWorld;
import com.finalist.test.osgi.HelloWorldImpl;

public class HelloWorldService implements BundleActivator {

	private HelloWorld service;	
	private ServiceRegistration registration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		service = new HelloWorldImpl();
		registration = context.registerService(HelloWorld.class.getName(),service, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		service = null;
	}
}
