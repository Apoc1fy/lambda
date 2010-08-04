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
public class UnhandledMessage extends IrcMessage {
    private String raw;

    public UnhandledMessage(User user, String command, String[] params) {
        super(user, command, params);

        raw = ":" + user.toString() + " " + command + " " + params[0];

        for(int x = 1; x < params.length; x++) {
            raw += " " + params[x];
        }
    }

    public String getRaw() {
        return raw;
    }

    public String toString() {
        return raw;
    }
}
