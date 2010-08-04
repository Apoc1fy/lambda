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
public class KickMessage extends IrcMessage {
    private String channel;
    private String target;
    private String reason;

    public KickMessage(User sender, String command, String[] params) {
        super(sender, command, params);
        
        channel = params[0];
        target = params[1];

        reason = params[2].substring(1);

        for(int x = 3; x < params.length; x++) {
            reason += " " + params[x];
        }
    }

    public final String getChannel() {
        return channel;
    }

    public final String getReason() {
        return reason;
    }

    public final String getTarget() {
        return target;
    }
}
