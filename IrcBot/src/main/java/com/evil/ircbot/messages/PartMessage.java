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
public class PartMessage extends IrcMessage {
    private String channel;
    private String reason;

    public PartMessage(User sender, String command, String[] params) {
        super(sender, command, params);

        channel = params[0];

        if(params.length > 1) {
            reason = params[1].substring(1);

            for(int x = 2; x < params.length; x++) {
                reason += " " + params[x];
            }
        } else {
            reason = "";
        }
    }

    public String getChannel() {
        return channel;
    }

    public String getReason() {
        return reason;
    }
}
