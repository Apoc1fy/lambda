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
public class NickMessage extends IrcMessage {
    private String nick;

    public NickMessage(User sender, String command, String[] params) {
        super(sender, command, params);

        this.nick = params[0].substring(1);
    }

    public final String getNick() {
        return nick;
    }
}
