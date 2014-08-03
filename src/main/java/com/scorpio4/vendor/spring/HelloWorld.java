package com.scorpio4.vendor.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.vendor.spring
 * @author lee
 * Date  : 1/07/2014
 * Time  : 12:47 AM
 */
public class HelloWorld {
	static protected final Logger log = LoggerFactory.getLogger(HelloWorld.class);

	@Resource(name="GreetingsEarthling")
	GreetingsEarthling greetingsEarthling;

	public HelloWorld() {
		log.debug("Hello Spring");
	}

	public HelloWorld(GreetingsEarthling earthling) {
		this.greetingsEarthling=earthling;
		log.debug("Hello! "+earthling.toString());
	}

	public boolean isWelcomed() {
		return greetingsEarthling!=null;
	}

	public GreetingsEarthling getGreetings() {
		return greetingsEarthling;
	}

	public void setGreetings(GreetingsEarthling greetingsEarthling) {
		this.greetingsEarthling = greetingsEarthling;
	}


	public String toString() {
		return isWelcomed()?"Hi! "+greetingsEarthling.toString():"Who are?";
	}
}
