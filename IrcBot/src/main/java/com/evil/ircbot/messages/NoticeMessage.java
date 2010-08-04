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
public class NoticeMessage extends IrcMessage {
    private String target;
    private String message;

    public NoticeMessage(User sender, String command, String[] params) {
        super(sender, command, params);

        target = params[0];

        message = params[1].substring(1);

        for(int x = 2; x < params.length; x++) {
            message += " " + params[x];
        }
    }

    public final String getTarget() {
        return target;
    }

    public final String getMessage() {
        return message;
    }
}
