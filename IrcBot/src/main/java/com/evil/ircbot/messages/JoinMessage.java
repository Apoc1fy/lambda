/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot.messages;

import com.evil.ircbot.User;

/**
 *
 * @author nicholas
 */
public class JoinMessage extends IrcMessage {
    private String channel;

    public JoinMessage(User sender, String command, String[] params) {
        super(sender, command, params);

        this.channel = params[0].substring(1);
    }

    public final String getChannel() {
        return channel;
    }
}
