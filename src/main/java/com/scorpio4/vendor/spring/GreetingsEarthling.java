package com.scorpio4.vendor.spring;

import java.util.Date;

/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.vendor.spring
 * @author lee
 * Date  : 1/07/2014
 * Time  : 1:23 AM
 */
public class GreetingsEarthling {

	public GreetingsEarthling() {
		System.out.println("Greetings @ "+new Date());
	}

	public String toString() {
		return "greetings earthling";
	}
}
