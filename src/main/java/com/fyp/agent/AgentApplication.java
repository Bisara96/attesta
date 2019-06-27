package com.fyp.agent;


import com.fyp.agent.utilities.Grid_HUBServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AgentApplication {

    @Value("${server.address}")
    private String address;

    private static String hostName;

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
        Grid_HUBServer hub = new Grid_HUBServer();
        hub.start(hostName);
	}

    @PostConstruct
    public void init() {
        hostName = address;
    }

}
