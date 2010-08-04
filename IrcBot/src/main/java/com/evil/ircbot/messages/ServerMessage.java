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
public class ServerMessage extends IrcMessage {
    public ServerMessage(User sender, String command, String[] params) {
        super(sender, command, params);
    }
}
