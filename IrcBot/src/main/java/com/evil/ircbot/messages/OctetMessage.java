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
public class OctetMessage extends IrcMessage {
    public OctetMessage(User user, String command, String[] params) {
        super(user, command, params);
    }

    public int getOctet() {
        return Integer.parseInt(command);
    }
}
