package com.scorpio4.vendor.spring

import com.scorpio4.runtime.MockEngine
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericApplicationContext
/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.vendor.spring
 * @author lee
 * Date  : 30/06/2014
 * Time  : 11:33 PM
 *
 *
 */
class RDFBeanDefinitionReaderTest extends GroovyTestCase {
	def HELLO_WORLD = "com/scorpio4/vendor/spring/HelloWorld.n3";

	void testRegister() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		assert 0 < beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.HelloWorld");
		BeanDefinition beanDef = beanie.beanFactory.getBeanDefinition("bean:com.scorpio4.vendor.spring.HelloWorld");
		println "Registered: "+beanDef;

		connection.close();
		engine.stop();
	}

	void testDefineBean() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		BeanDefinition beanDef = beanie.defineBean("com.scorpio4.vendor.spring.HelloWorld");
		println "Defined: "+beanDef;

		connection.close();
		engine.stop();
	}

	void testLoadDefinitions() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();
		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		def loaded = beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.HelloWorld");
		loaded+= beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.GreetingsEarthling");
		println "Loaded: "+loaded;
		def bean = applicationContext.getBean("bean:com.scorpio4.vendor.spring.HelloWorld");
		assert bean!=null;
		assert com.scorpio4.vendor.spring.HelloWorld.isInstance(bean);
		assert bean.isWelcomed();
		println "Hello Bean: "+bean;

		connection.close();
		engine.stop();
	}
}
