/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot.scripts;

import com.evil.ircbot.IrcClient;
import com.evil.ircbot.IrcOutput;
import com.evil.ircbot.messages.IrcMessage;
import com.evil.ircbot.messages.KickMessage;
import com.evil.ircbot.messages.ModeMessage;
import com.evil.ircbot.messages.NickMessage;
import com.evil.ircbot.messages.OctetMessage;
import com.evil.ircbot.messages.PartMessage;
import com.evil.ircbot.messages.PrivateMessage;
import com.evil.ircbot.messages.ServerMessage;
import com.evil.ircbot.messages.UnhandledMessage;
import com.evil.ircbot.script.Script;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author nicholas
 */
public class CoreHandlerPlugin extends Script {
    @Override
    public String getName() {
        return "CoreHandler";
    }

    @Override
    public void run(IrcOutput output, IrcMessage message) {
        String command = message.getCommand();

        if(message instanceof ServerMessage) {
            if(command.equals("PING")) {
                output.send("PONG " + message.getParams()[0], true);
            }
        } else if(message instanceof PrivateMessage) {
            PrivateMessage msg = (PrivateMessage) message;
            
            String s = msg.getMessage();

            System.out.println("[" + msg.getTarget() + "][" + msg.getSender().getNick() + "]: " + s);
        } else if(message instanceof UnhandledMessage) {
            System.out.println(((UnhandledMessage) message));
        } else if(message instanceof OctetMessage) {
            OctetMessage omsg = (OctetMessage) message;

            switch(omsg.getOctet()) {
                case 5:
                    output.startOutput();
                    break;
            }
        } else if(message instanceof ModeMessage) {
            ModeMessage msg = (ModeMessage) message;

            Map<Character, Boolean> modes = msg.getModes();

            System.out.print(msg.getSender().getNick() + " sets mode ");

            String add = "", sub = "";

            for(Character c : modes.keySet()) {
                if(modes.get(c)) {
                    add += c;
                } else {
                    sub += c;
                }
            }

            if(!add.equals("")) {
                System.out.print("+" + add);
            }

            if(!sub.equals("")) {
                System.out.print("-" + sub);
            }

            for(String s : msg.getArguments()) {
                System.out.print(" " + s);
            }

            System.out.println();
        } else if(message instanceof PartMessage) {
            PartMessage msg = (PartMessage) message;

            System.out.println(msg.getSender().getNick() + " has left " + msg.getChannel() + " (" + msg.getReason() + ")");
        } else if(message instanceof KickMessage) {
            KickMessage msg = (KickMessage) message;

            System.out.println(msg.getSender().getNick() + " has kicked " + msg.getTarget() + " from " + msg.getChannel() + " (" + msg.getReason() + ")");
        } else if(message instanceof NickMessage) {
            NickMessage msg = (NickMessage) message;

            System.out.println(msg.getSender().getNick() + " is now known as " + msg.getNick());
        }
    }
}
