package org.transfer.broadcaster;

import org.transfer.broadcaster.console.Application;
import org.transfer.broadcaster.console.commands.RunCommand;
import org.transfer.broadcaster.console.io.StandardInput;
import org.transfer.broadcaster.console.io.StandardOutput;

public class AgentApplication {

    public static void main(String[] args) {

        Application app = new Application(new StandardInput(args), new StandardOutput());

        app.setName("Transfer Agent");
        app.setVersion("v1.0");
        app.setProgramName("transfer-broadcaster");

        app.addCommand(new RunCommand());

        app.run();

    }
}
