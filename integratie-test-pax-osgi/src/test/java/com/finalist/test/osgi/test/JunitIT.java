package com.finalist.test.osgi.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.LibraryOptions.junitBundles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.finalist.test.osgi.HelloWorld;



@RunWith( JUnit4TestRunner.class )

//One OSGi container instance for each test
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class JunitIT {
	
	private static final Logger log = LoggerFactory.getLogger(JunitIT.class);

    @Configuration()
    public Option[] config()
    {
        return options(
        	
        	//We are using the log api in this integration test, so make sure that the logging service is available in the OSGi container
        	mavenBundle("org.ops4j.pax.logging","pax-logging-api","1.5.3"),
        	mavenBundle("org.ops4j.pax.logging","pax-logging-service","1.5.3"),
        	
        	//The bundle we are testing
        	bundle("file:target/integratie-test-pax-osgi-0.0.1-SNAPSHOT.jar"),
            junitBundles()
        );
    }

    @Test
    public void printBundles ( BundleContext ctx) {
    	for (Bundle bundle : ctx.getBundles()) {
        	log.info("-- Found bundle " + bundle.getSymbolicName() + " status " + bundle.getState());
    	}
    }
    
    @Test
    public void testHelloWorlPrinted( BundleContext ctx) {
    	ServiceReference serviceReference = ctx.getServiceReference(HelloWorld.class.getName());
    	assertNotNull("A service reference must be available for " + HelloWorld.class.getName(),serviceReference);
    	Object service = ctx.getService(serviceReference);
    	assertTrue("Service must be of type HelloWorld",service instanceof HelloWorld);
    	HelloWorld helloWorld = (HelloWorld) service;
    	assertEquals("Service must print Hello World","Hello World",helloWorld.print());
    }
}