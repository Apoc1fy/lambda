/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evil.ircbot.script;

import com.evil.ircbot.IrcOutput;
import com.evil.ircbot.messages.IrcMessage;
import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author nicholas
 */
public class DefaultScriptHandler implements ScriptHandler {

    private static final Logger logger = Logger.getLogger(DefaultScriptHandler.class.getName());
    private GroovyClassLoader loader;
    private File directory;
    private Map<String, Script> plugins = new HashMap<String, Script>();

    public DefaultScriptHandler() {
        loader = new GroovyClassLoader();

        directory = new File("./src/main/java/com/evil/ircbot/scripts/");
    }

    @Override
    public void loadPlugins() {
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".groovy");
            }
        };

        for (String s : directory.list(filter)) {
            System.out.println("Loading " + s);
            loadPlugin(s);
        }
    }

    @Override
    public void loadPlugin(String name) {
        try {
            Class<?> c = loader.parseClass(new File(directory, name));

            if (Script.class.isAssignableFrom(c)) {
                try {
                    Script p = (Script) c.newInstance();


                    if(p.setup()) {
                        plugins.put(p.getName(), p);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(DefaultScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DefaultScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (CompilationFailedException ex) {
            Logger.getLogger(DefaultScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DefaultScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run(IrcOutput output, IrcMessage message) {
        for(Script p : plugins.values()) {
            p.run(output, message);
        }
    }

    @Override
    public void clear() {
        for(Script p : plugins.values()) {
            if(!p.destroy()) {
                Logger.getLogger(DefaultScriptHandler.class.getName()).log(Level.WARNING, "Error destroying " + p.getName());
            }
        }

        plugins.clear();
        plugins = null;

        System.gc();

        plugins = new HashMap<String, Script>();
    }

    @Override
    public void reload() {
        clear();
        loadPlugins();
    }

    @Override
    public String[] getPlugins() {
        return plugins.keySet().toArray(new String[0]);
    }
}
