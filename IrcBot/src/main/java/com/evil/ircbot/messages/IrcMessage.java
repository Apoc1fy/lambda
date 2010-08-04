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
public class IrcMessage {
    protected User sender;
    protected String command;
    protected String[] params;

    public IrcMessage(User sender, String command, String[] params) {
        this.sender = sender;
        this.command = command;

        this.params = new String[params.length];
        System.arraycopy(params, 0, this.params, 0, params.length);
    }

    public User getSender() {
        return sender;
    }

    public String getCommand() {
        return command;
    }

    public String[] getParams() {
        return params;
    }
}
