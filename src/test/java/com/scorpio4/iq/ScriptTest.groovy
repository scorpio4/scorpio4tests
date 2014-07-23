package com.scorpio4.iq

import com.scorpio4.assets.Asset
import com.scorpio4.iq.exec.Scripting
import com.scorpio4.vocab.COMMONS

import java.util.concurrent.Future
/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * @author lee
 * Date  : 17/06/2014
 * Time  : 9:44 PM
 *
 *
 */
class ScriptTest extends GroovyTestCase {

    void testScriptAsset() {
        Scripting script = new Scripting();
	    Asset asset = new Asset("urn:test", "return 'hello world'", COMMONS.MIME_GROOVY);
        Future done = script.execute(asset, new HashMap());
        assert done.get()!=null;
        assert done.get() == "hello world";
    }

}
