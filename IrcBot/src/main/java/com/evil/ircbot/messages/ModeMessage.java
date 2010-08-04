/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot.messages;

import com.evil.ircbot.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nicholas
 */
public class ModeMessage extends IrcMessage {
    private Map<Character, Boolean> modes = new HashMap<Character, Boolean>();
    private List<String> arguments = new ArrayList<String>();
    private String target;

    public ModeMessage(User sender, String command, String[] params) {
        super(sender, command, params);

        target = params[0];

        boolean set = true;
        String modeString = params[1];

        if(modeString.startsWith(":")) {
            modeString = modeString.substring(1);
        }

        for(char c : modeString.toCharArray()) {
            if(c == '+') {
                set = true;
            } else if(c == '-') {
                set = false;
            } else {
                modes.put(c, set);
            }
        }

        if(params.length > 2) {
            for(int x = 2; x < params.length; x++) {
                arguments.add(params[x]);
            }
        }
    }

    public Map<Character, Boolean> getModes() {
        return modes;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getTarget() {
        return target;
    }
}
