package com.finalist.test.osgi.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.finalist.test.osgi.HelloWorld;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.LibraryOptions.*;



@RunWith( JUnit4TestRunner.class )

//One OSGi container instance for each test
//@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
//One OSGi container instance to run all tests
@ExamReactorStrategy( EagerSingleStagedReactorFactory.class )
public class JunitIT {
	
	private static final Logger log = LoggerFactory.getLogger(JunitIT.class);

    @Configuration()
    public Option[] config()
    {
        return options(
        	
        	//Logging
        	mavenBundle("org.ops4j.pax.logging","pax-logging-api","1.5.3").start(true),
        	mavenBundle("org.ops4j.pax.logging","pax-logging-service","1.5.3").start(true),
        		
        	//Blueprint
        	mavenBundle("org.apache.felix","org.apache.felix.configadmin","1.2.4").start(true),
        	mavenBundle("org.apache.aries.blueprint","org.apache.aries.blueprint","0.2-incubating").start(true),
        	
        	bundle("file:target/integratie-test-pax-blueprint-0.0.1-SNAPSHOT.jar").start(true),
        	waitForFrameworkStartup(),
            junitBundles()
        );
    }

    @Test
    public void printBundles ( BundleContext ctx) {
    	log.info("------- Start printing bundles ------------");
    	for (Bundle bundle : ctx.getBundles()) {
        	log.info("-- Start printing bundle " + bundle.getSymbolicName() + " status " + bundle.getState());
        	if (bundle.getSymbolicName().equals("com.finalist.test.osgi.integratie-test-pax-blueprint")) {
        		for (ServiceReference serviceReference : bundle.getRegisteredServices()) {
        			Object service = ctx.getService(serviceReference);
        			log.info("--- Service " + service.getClass().getName());
        		}        		
        	} else {
        		log.info("--- Not printing services.");
        	}
        	log.info("-- Finished printing bundle " + bundle.getSymbolicName());
    	}
    	log.info("------- Finished printing bundles ------------");
    }
    
    @Test
    public void verifyConnectionFactoryStarted( BundleContext ctx) {
    	ServiceReference serviceReference = ctx.getServiceReference(HelloWorld.class.getName());
    	assertNotNull("A service reference must be available for nl.onvz.test.HelloWorld",serviceReference);
    	Object service = ctx.getService(serviceReference);
    	assertTrue("Service must be of type HelloWorld",service instanceof HelloWorld);
    	HelloWorld helloWorld = (HelloWorld) service;
    	assertEquals("Service must print Hello World","Hello World",helloWorld.print());
    }   
}