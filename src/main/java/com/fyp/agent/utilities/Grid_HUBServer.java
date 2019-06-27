package com.fyp.agent.utilities;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Grid_HUBServer {

    @Autowired
    private Environment env;

    public void start(String hostName) {
        try {
//            String hostName = InetAddress.getLoopbackAddress().getHostName();
//          String config = this.getClass().getClassLoader().getResource("selenium-server/hub-config.json").getPath();
            URL server_jar_url = this.getClass().getClassLoader().getResource("selenium-server/selenium-server-standalone-3.141.59.jar");
            String server_jar = new File(server_jar_url.toURI()).getAbsolutePath();
            String cmd = String.format("java -jar %s -role hub -cleanUpCycle 10000 -host %s", server_jar, hostName);
            System.out.println(hostName);
            CommandLine cmdLine = CommandLine.parse(cmd);
            DefaultExecutor executor = new DefaultExecutor();
            int exitValue = executor.execute(cmdLine);
//            String output = outputStream.toString();
        } catch (IOException e){

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Grid_HUBServer s = new Grid_HUBServer();
        s.start("");
    }
}
