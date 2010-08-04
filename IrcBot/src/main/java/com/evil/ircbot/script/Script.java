/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot.script;

import com.evil.ircbot.IrcOutput;
import com.evil.ircbot.messages.IrcMessage;

/**
 *
 * @author nicholas
 */
public abstract class Script {
    public abstract String getName();
    public double getVersion() { return 1.0; }

    public boolean setup() { return true; }
    public abstract void run(IrcOutput output, IrcMessage message);
    public boolean destroy() { return true; }

    @Override
    public String toString() {
        return getName();
    }
}
