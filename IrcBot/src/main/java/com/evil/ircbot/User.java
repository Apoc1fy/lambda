/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot;

import java.util.Arrays;

/**
 *
 * @author nicholas
 */
public class User {
    public static User parse(String raw) {
        String[] data = raw.substring(1).split("[!@]");

        if(data.length == 1) {
            return new User(data[0], "", "");
        } else {
            return new User(data[0], data[1], data[2]);
        }
    }

    public static User forName(String name) {
        return new User(name, "", "");
    }

    private String nick;
    private String user;
    private String host;

    public User(String nick, String user, String host) {
        this.nick = nick;
        this.user = user;
        this.host = host;
    }

    public final String getNick() {
        return nick;
    }

    public final String getUser() {
        return user;
    }

    public final String getHost() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof User) {
            return ((User) o).nick.equals(nick);
        }

        return false;
    }

    public String toString() {
        return nick + "!" + user + "@" + host;
    }
}
