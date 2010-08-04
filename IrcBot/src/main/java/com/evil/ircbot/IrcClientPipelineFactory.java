/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 *
 * @author nicholas
 */
public class IrcClientPipelineFactory implements ChannelPipelineFactory {
    private IrcClient client;

    public IrcClientPipelineFactory(IrcClient client) {
        this.client = client;
    }

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));

        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        pipeline.addLast("handler", new IrcClientHandler(client));
        return pipeline;
    }
}
