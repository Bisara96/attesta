package com.fyp.agent.utilities;

import com.fyp.agent.models.Agent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class CreateAgent {

    private static final Logger LOGGER = Logger.getLogger( CreateAgent.class.getName() );
    private String hubUrl;
    private Agent agent;
    private String serverAddress;

    public CreateAgent(String hubUrl, String serverAddress, Agent agent) {
        this.hubUrl = hubUrl;
        this.serverAddress = serverAddress;
        this.agent = agent;
    }

    private JSONObject getConfigObject() throws JSONException {
        JSONObject config = new JSONObject();
        config.put("hub", hubUrl);
        config.put("agentType", agent.getType());
        config.put("db_id", agent.getId());
        config.put( "server_address", serverAddress);

        JSONArray capabilities = new JSONArray();

        JSONObject browser = new JSONObject();
        switch (agent.getBrowser().toLowerCase()){
            case "chrome":
                browser.put("browserName", "chrome");
                break;
            case "firefox":
                browser.put("browserName", "firefox");
                break;
            case "ie":
                browser.put("browserName", "internet explorer");
                break;
            case "safari":
                browser.put("browserName", "safari");
                break;
            default:
                browser.put("browserName", "chrome");
        }
        switch (agent.getPlatform().toLowerCase()){
            case "windows":
                browser.put("platform", "WINDOWS");
                break;
            case "mac":
                browser.put("platform", "MAC");
                break;
            case "linux":
                browser.put("platform", "LINUX");
                break;
            default:
                browser.put("platform", "WINDOWS");
        }
        browser.put("maxInstances", "3");
        browser.put("version", "");
        browser.put("applicationName", agent.getName());

        capabilities.put(browser);
        config.put("capabilities", capabilities);
        return config;
    }

    public void updateConfig() {
        try {
            LOGGER.info("Updating agent configurations");
            String str = getConfigObject().toString();
            URL res = getClass().getClassLoader().getResource("agent-instance/node-conf.json");
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getAbsolutePath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println("Failed");
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }

    public String bundleAgent() {
        try {
            LOGGER.info("Bundling agent");
            String absolutePath = getResourceAbsolutepath("/agent-instance");
            String outputFolderPath = getResourceAbsolutepath("/generated-agents");
            ZipDirectory zipD = new ZipDirectory(absolutePath, agent.getName(), outputFolderPath);
            zipD.zip();
            return agent.getName()+".zip";
        } catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) {

//        CreateAgent agent = new CreateAgent("","","","","tessss");
//        agent.updateConfig();
//        agent.bundleAgent();
    }

    public String getResourceAbsolutepath(String name) {
        URL url = getClass().getResource(name);
        try {
            return new File(url.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.warning("ERROR");
            return null;
        }
    }

}
