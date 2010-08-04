/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot.scripts;

import com.evil.ircbot.IrcOutput;
import com.evil.ircbot.messages.IrcMessage;
import com.evil.ircbot.messages.PrivateMessage;
import com.evil.ircbot.script.Script;

/**
 *
 * @author nicholas
 */
public class BasicCommandsScript extends Script {
    @Override
    public String getName() {
        return "BasicCommands";
    }

    @Override
    public void run(IrcOutput output, IrcMessage message) {
        if(message instanceof PrivateMessage) {
            PrivateMessage msg = (PrivateMessage) message;

            String s = msg.getMessage();
            String[] data = s.split(" ");

            if(data[0].equals("!say")) {
                output.sendMessage(msg.getTarget(), "[" + msg.getSender().getNick() + "]: " + s.substring(s.indexOf(" ") + 1));
            }
        }
    }
}
