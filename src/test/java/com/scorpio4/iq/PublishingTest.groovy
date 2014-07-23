package com.scorpio4.iq

import com.scorpio4.assets.Asset
import com.scorpio4.iq.exec.Templating
import com.scorpio4.vocab.COMMONS

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * @author lee
 * Date  : 17/06/2014
 * Time  : 10:02 PM
 *
 *
 */
class PublishingTest extends GroovyTestCase {

    void testWith() {
        def publisher = new Templating();
	    Asset template = new Asset("urn:test", "hello \${greeting}", COMMONS.MIME_PLAIN);
        def future = publisher.execute(template, [ "greeting": "world"])
        assert future!=null;
        Writable writable = future.get();
        assert writable!=null;
        assert writable.toString().equals("hello world");
    }
}
