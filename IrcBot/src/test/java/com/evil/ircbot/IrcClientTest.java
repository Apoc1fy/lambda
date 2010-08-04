/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evil.ircbot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author nicholas
 */
public class IrcClientTest extends TestCase {

    public IrcClientTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(IrcClientTest.class);
    }

    public void testIrcClient() {
        IrcClient client = IrcClient.getInstance();
        Channel connected = client.connect("irc.moparisthebest.com");
        client.close();

        assertNotNull(connected);
    }
}
