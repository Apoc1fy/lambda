/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.evil.ircbot;

import java.util.LinkedList;
import java.util.Queue;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author nicholas
 */
public class IrcOutput {
    private Channel channel;
    private Queue<String> queue = new LinkedList<String>();
    private final Object lock = new Object();

    private IrcOutputSender sender = null;

    public IrcOutput(Channel channel) {
        this.channel = channel;
    }

    public final Channel getChannel() {
        return channel;
    }

    public final void send(String data) {
        send(data, false);
    }
    
    public final void send(String data, boolean now) {
        if(now) {
            channel.write(data + "\r\n");
        } else {
            queue.add(data + "\r\n");

            synchronized(lock) {
                lock.notify();
            }
        }
    }

    public final void sendMessage(String receiver, String message) {
        send("PRIVMSG " + receiver + " :" + message + "\r\n");
    }

    public final void sendNotice(String receiver, String message) {
        send("NOTICE " + receiver + " :" + message + "\r\n");
    }

    public final void sendJoin(String channel) {
        sendJoin(channel, "");
    }

    public final void sendJoin(String channel, String key) {
        send("JOIN " + channel + " :" + key);
    }

    public final void sendPart(String channel) {
        sendPart(channel, "");
    }

    public final void sendPart(String channel, String message) {
        send("PART " + channel + " :" + message);
    }

    public final void sendQuit() {
        sendQuit("Goodbye.");
    }

    public final void sendQuit(String message) {
        send("QUIT :" + message);
    }

    public final void sendNick(String nick) {
        send("NICK " + nick);
    }

    public final void startOutput() {
        if(sender == null) {
            sender = new IrcOutputSender();
            new Thread(sender).start();
        }
    }

    class IrcOutputSender extends Thread {

        @Override
        public void run() {
            while(channel.isConnected()) {
                if(queue.isEmpty()) { // For the initial start of the thread.
                    synchronized(lock) {
                        try {
                            lock.wait();
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                while(!queue.isEmpty()) {
                    String s = queue.poll();

                    if(s != null) { // Just to be safe.
                        channel.write(s);
                    }
                }
            }
        }
    }
}
