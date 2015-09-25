package org.transfer.agent;

import org.transfer.agent.console.Application;
import org.transfer.agent.console.commands.RunCommand;
import org.transfer.agent.console.io.StandardInput;
import org.transfer.agent.console.io.StandardOutput;

public class AgentApplication {

    public static void main(String[] args) {

        Application app = new Application(new StandardInput(args), new StandardOutput());

        app.setName("Transfer Agent");
        app.setVersion("v1.0");
        app.setProgramName("transfer-agent");

        app.addCommand(new RunCommand());

        app.run();

    }
}
