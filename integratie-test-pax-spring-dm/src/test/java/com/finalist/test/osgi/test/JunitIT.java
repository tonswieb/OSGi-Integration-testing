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
//        	mavenBundle("org.apache.felix","org.apache.felix.fileinstall","3.0.2").start(true),
        		
        	//Blueprint
//        	mavenBundle("org.apache.felix","org.apache.felix.configadmin","1.2.4").start(true),
//        	mavenBundle("org.apache.aries.blueprint","org.apache.aries.blueprint","0.2-incubating").start(true),
        	
        	mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.aopalliance","1.0_4").start(true),
        	mavenBundle("org.springframework","spring-core","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-asm","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-expression","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-beans","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-aop","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-context","3.0.5.RELEASE").start(true),
        	mavenBundle("org.springframework","spring-context-support","3.0.5.RELEASE").start(true),
        	mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.cglib","2.1_3_6").start(true),
        	mavenBundle("org.springframework.osgi","spring-osgi-io","1.2.0").start(true),
        	mavenBundle("org.springframework.osgi","spring-osgi-core","1.2.0").start(true),
        	mavenBundle("org.springframework.osgi","spring-osgi-extender","1.2.0").start(true),
        	mavenBundle("org.springframework.osgi","spring-osgi-annotation","1.2.0").start(true),
        	mavenBundle("org.apache.karaf.deployer","org.apache.karaf.deployer.spring","2.1.4-fuse-00-09").start(true),        	
        		
//        	mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.aopalliance","1.0_4").start(true),
//        	mavenBundle("org.springframework","spring-core","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-asm","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-expression","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-beans","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-aop","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-context","3.0.5.RELEASE").start(true),
//        	mavenBundle("org.springframework","spring-context-support","3.0.5.RELEASE").start(true),
        	
//        	mavenBundle("org.springframework.osgi","spring-osgi-io","2.0.0.M1").start(true),
//        	mavenBundle("org.springframework.osgi","spring-osgi-core","2.0.0.M1").start(true),
//        	mavenBundle("org.springframework.osgi","spring-osgi-extender","2.0.0.M1").start(true),
//        	mavenBundle("org.springframework.osgi","spring-osgi-annotation","2.0.0.M1").start(true),        	
//        	mavenBundle("org.springframework.osgi","spring-osgi-extensions-annotation","2.0.0.M1").start(true),        	
//        	0 32 org.eclipse.osgi 3.6.0.v20100517
//        	1 32 com.springsource.org.aopalliance 1.0.0
//        	2 32 com.springsource.org.apache.log4j 1.2.15
//        	3 32 com.springsource.junit 3.8.2
//        	4 32 com.springsource.org.objectweb.asm 2.2.3
//        	5 32 com.springsource.slf4j.api 1.5.6
//        	6 4 com.springsource.slf4j.log4j 1.5.6
//        	7 32 com.springsource.slf4j.org.apache.commons.logging 1.5.6
//        	8 32 org.springframework.aop 3.0.0.RC1
//        	9 32 org.springframework.asm 3.0.0.RC1
//        	10 32 org.springframework.beans 3.0.0.RC1
//        	11 32 org.springframework.context 3.0.0.RC1
//        	12 32 org.springframework.core 3.0.0.RC1
//        	13 32 org.springframework.expression 3.0.0.RC1
//        	14 32 org.springframework.test 3.0.0.RC1
        	
        	bundle("file:target/integratie-test-pax-spring-dm-0.0.1-SNAPSHOT.jar").start(true),
        	waitForFrameworkStartup(),
            junitBundles()
        );
    }

    @Test
    public void printBundles ( BundleContext ctx) {
    	log.info("------- Start printing bundles ------------");
    	for (Bundle bundle : ctx.getBundles()) {
        	log.info("-- Start printing bundle " + bundle.getSymbolicName() + " status " + bundle.getState());
        	if (bundle.getSymbolicName().equals("com.finalist.test.osgi.integratie-test-pax-spring-dm")) {
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