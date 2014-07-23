package com.scorpio4.vendor.spring
import com.scorpio4.runtime.MockEngine
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
import org.springframework.context.support.GenericApplicationContext
import org.springframework.jndi.support.SimpleJndiBeanFactory
import org.springframework.mock.jndi.SimpleNamingContextBuilder
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
	def HELLO_WORLD_N3 = "com/scorpio4/vendor/spring/HelloWorld.n3";
	def HELLO_WORLD_CLASS = "com.scorpio4.vendor.spring.HelloWorld";

	void testRegister() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD_N3);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		assert 0 < beanie.loadBeanDefinitions("bean:"+HELLO_WORLD_CLASS);
		BeanDefinition beanDef = beanie.beanFactory.getBeanDefinition("bean:com.scorpio4.vendor.spring.HelloWorld");
		println "Registered: "+beanDef;

		connection.close();
		engine.stop();
	}

	void testDefineBean() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD_N3);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		BeanDefinition beanDef = beanie.defineBean(HELLO_WORLD_CLASS);
		assert beanDef!=null;
		assert beanDef.getBeanClassName() == HELLO_WORLD_CLASS

		connection.close();
		engine.stop();
	}

	void testLoadDefinitions() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD_N3);
		def connection = engine.getRepository().getConnection();

		ApplicationContext applicationContext = new GenericApplicationContext();
		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		def loaded = beanie.loadBeanDefinitions("bean:"+HELLO_WORLD_CLASS);
		loaded+= beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.GreetingsEarthling");
		println "Loaded: "+loaded;
		def bean = applicationContext.getBean("bean:"+HELLO_WORLD_CLASS);
		assert bean!=null;
		assert com.scorpio4.vendor.spring.HelloWorld.isInstance(bean);
		assert ((HelloWorld)bean).isWelcomed();
		println "Hello Bean: "+bean;

		connection.close();
		engine.stop();
	}

	void testJNDIInjection() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD_N3);
		def connection = engine.getRepository().getConnection();

		SimpleNamingContextBuilder jndi = new SimpleNamingContextBuilder();
		jndi.bind("GreetingsEarthling", new GreetingsEarthling() )
		jndi.activate();

		def jndiBeanFactory  = new SimpleJndiBeanFactory();

		def lookup2 = jndiBeanFactory.getBean("GreetingsEarthling");
		assert lookup2!=null;
		assert lookup2 instanceof GreetingsEarthling

		ApplicationContext applicationContext = new GenericApplicationContext();
		def rootBeanDef = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
		rootBeanDef.getPropertyValues().add("alwaysUseJndiLookup", true);
		applicationContext.registerBeanDefinition("root", rootBeanDef);
		applicationContext.refresh();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		beanie.loadBeanDefinitions("urn:com.scorpio4.vendor.spring.HelloWorld");

		def bean = applicationContext.getBean("urn:com.scorpio4.vendor.spring.HelloWorld");
		assert bean!=null;
		assert bean instanceof HelloWorld
		assert bean.toString().equals( "Hi! "+lookup2 );

		connection.close();
		engine.stop();
	}

	public void testCachedBeanInjection() {
		MockEngine engine = new MockEngine();
		engine.provision(HELLO_WORLD_N3);
		def connection = engine.getRepository().getConnection();

		def beanFactory = new CachedBeanFactory();
		beanFactory.bind("Greetings", new GreetingsEarthling() )

		ApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.getBeanFactory().setParentBeanFactory(beanFactory)

		def bean1 = applicationContext.getBean("Greetings");
		assert bean1!=null;


		def rootBeanDef = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
		rootBeanDef.getPropertyValues().add("alwaysUseJndiLookup", false);
		CommonAnnotationBeanPostProcessor ca;
//		ca.setBeanFactory()
		rootBeanDef.getPropertyValues().add("beanFactory", beanFactory);
		applicationContext.registerBeanDefinition("root", rootBeanDef);
//		applicationContext.refresh();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		def definitions = beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.HelloWorld");
		println "Defined: ${definitions}"
		applicationContext.refresh();

		def bean2 = applicationContext.getBean("bean:com.scorpio4.vendor.spring.HelloWorld");
		assert bean2!=null;
		assert bean2 instanceof HelloWorld
		assert bean2.toString().equals( "Hi! "+bean1.toString() );

		connection.close();
		engine.stop();
	}

}
