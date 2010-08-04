/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot;

import com.evil.ircbot.script.ScriptHandler;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 *
 * @author nicholas
 */
public class IrcClient {

    private static IrcClient instance = null;
    private Map<String, IrcOutput> channels = new HashMap<String, IrcOutput>();
    private ScriptHandler handler;
    private ClientBootstrap bootstrap = null;

    public static IrcClient getInstance() {
        if(instance == null) instance = new IrcClient();
        return instance;
    }

    private IrcClient() {
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new IrcClientPipelineFactory(this));
    }
    
    public final Channel connect(String host) {
        return connect(host, 6667);
    }

    public final Channel connect(String host, int port) {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        Channel channel = future.awaitUninterruptibly().getChannel();

        if(!future.isSuccess()) {
            bootstrap.releaseExternalResources();

            return null;
        }

        int x = host.indexOf(".") + 1;
        int y = host.indexOf(".", x);

        channels.put(host.substring(x, y), new IrcOutput(channel));

        return channel;
    }

    public final void close() {
        for(IrcOutput channel : channels.values()) {
            channel.sendQuit();
            channel.getChannel().close().awaitUninterruptibly();
            bootstrap.releaseExternalResources();
        }
    }

    public final void setScriptHandler(ScriptHandler handler) {
        this.handler = handler;
    }

    public final ScriptHandler getScriptHandler() {
        return handler;
    }

    public final IrcOutput get(String network) {
        return channels.get(network);
    }
}
