/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evil.ircbot;

import com.evil.ircbot.messages.IrcMessage;
import com.evil.ircbot.messages.JoinMessage;
import com.evil.ircbot.messages.KickMessage;
import com.evil.ircbot.messages.ModeMessage;
import com.evil.ircbot.messages.NickMessage;
import com.evil.ircbot.messages.NoticeMessage;
import com.evil.ircbot.messages.OctetMessage;
import com.evil.ircbot.messages.PartMessage;
import com.evil.ircbot.messages.PrivateMessage;
import com.evil.ircbot.messages.ServerMessage;
import com.evil.ircbot.messages.UnhandledMessage;
import com.evil.ircbot.script.ScriptHandler;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 *
 * @author nicholas
 */
public class IrcClientHandler extends SimpleChannelHandler {

    private static final Logger logger = Logger.getLogger(IrcClientHandler.class.getName());
    private IrcClient client;

    public IrcClientHandler(IrcClient client) {
        this.client = client;
    }

    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info(e.toString());
        }

        super.handleUpstream(ctx, e);
    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        String message = (String) e.getMessage();
        String[] data = message.split(" ", 3);

        String s = ctx.getChannel().getRemoteAddress().toString();

        int x = s.indexOf(".") + 1;
        int y = s.indexOf(".", x);

        String host = s.substring(x, y);

        IrcOutput output = client.get(host);

        User sender = null;
        String command;
        String[] params;

        int idx = 0;

        if(data[idx].startsWith(":")) {
            sender = User.parse(data[idx++]);
        }

        command = data[idx++];
        params = data[idx].split(" ");

        ScriptHandler handler = client.getScriptHandler();

        IrcMessage msg;

        if(sender == null) {
            msg = new ServerMessage(sender, command, params);
        } else if(command.equals("PRIVMSG")) {
            msg = new PrivateMessage(sender, command, params);
            PrivateMessage pm = (PrivateMessage)msg;

            s = pm.getMessage();

            if(s.startsWith("!load")) {
                try {
                    IrcClient.getInstance().getScriptHandler().loadPlugin(s.split(" ")[1]);
                } catch(Exception ee) {
                    ee.printStackTrace();
                }
            } else if(s.equals("!reload")) {
                try {
                    IrcClient.getInstance().getScriptHandler().reload();
                    output.sendMessage(pm.getTarget(), "Reloaded plugins.");
                } catch(Exception ee) {
                    ee.printStackTrace();
                }
            } else if(s.equals("!list")) {
                output.sendMessage(pm.getTarget(), "Plugins: " + Arrays.toString(IrcClient.getInstance().getScriptHandler().getPlugins()));
            }
        } else if(command.equals("NOTICE")) {
            msg = new NoticeMessage(sender, command, params);
        } else if(command.matches("\\d{3}")) {
            msg = new OctetMessage(sender, command, params);
        } else if(command.equals("JOIN")) {
            msg = new JoinMessage(sender, command, params);
        } else if(command.equals("MODE")) {
            msg = new ModeMessage(sender, command, params);
        } else if(command.equals("PART")) {
            msg = new PartMessage(sender, command, params);
        } else if(command.equals("KICK")) {
            msg = new KickMessage(sender, command, params);
        } else if(command.equals("NICK")) {
            msg = new NickMessage(sender, command, params);
        } else {
            msg = new UnhandledMessage(sender, command, params);
        }

        handler.run(output, msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
}
