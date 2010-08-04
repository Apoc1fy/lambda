
import com.evil.ircbot.IrcClient;
import com.evil.ircbot.IrcOutput;
import com.evil.ircbot.script.DefaultScriptHandler;
import com.evil.ircbot.script.ScriptHandler;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nicholas
 */
public class Main {

    public static void main(String[] args) {
        IrcClient client = IrcClient.getInstance();

        DefaultScriptHandler handler = new DefaultScriptHandler();

        client.setScriptHandler(handler);
        handler.loadPlugins();

        client.connect("irc.moparisthebest.com");

        IrcOutput o = client.get("moparisthebest");

        o.send("NICK lambda", true);
        o.send("USER lambda 8 * lambda", true);
        o.sendMessage("NickServ", "IDENTIFY angie9283");
        o.sendJoin("#lambda");
    }
}
