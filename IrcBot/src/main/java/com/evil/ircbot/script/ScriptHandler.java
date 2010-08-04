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
public interface ScriptHandler {
    public void loadPlugins();
    public void loadPlugin(String name);
    public void run(IrcOutput output, IrcMessage message);
    public void clear();
    public void reload();
    public String[] getPlugins();
}
