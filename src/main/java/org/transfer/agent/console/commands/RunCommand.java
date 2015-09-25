package org.transfer.agent.console.commands;

import org.transfer.agent.console.Command;
import org.transfer.agent.console.io.Input;
import org.transfer.agent.console.io.Output;
import org.transfer.agent.messenger.ActivityMessenger;
import org.transfer.agent.meteor.Client;
import org.transfer.agent.meteor.DefaultClient;
import org.transfer.agent.models.TransferProcess;
import org.transfer.agent.system.ProcessRunner;
import org.transfer.agent.system.StreamConsumer;

import java.io.IOException;
import java.net.URISyntaxException;

public class RunCommand implements Command {

    @Override
    public String getName() {
        return "run";
    }

    @Override
    public String getDescription() {
        return "Runs manifest";
    }

    @Override
    public void execute(Input input, Output output) {
        ProcessRunner runner = new ProcessRunner(
                input.getArgument(1),
                input.getArgument(2),
                input.getArgument(3),
                input.getArgument(4));

        TransferProcess process = new TransferProcess(input.getArgument(2));

        Client client = null;

        try {
            client = new DefaultClient(input.getArgument(5), Integer.parseInt(input.getArgument(6)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final ActivityMessenger messenger = new ActivityMessenger(process, client);

        runner.setInputStreamConsumer(new StreamConsumer() {
            @Override
            public void consume(String line) {
                messenger.push(line);
            }

            @Override
            public void done() {}
        });

        runner.setErrorStreamConsumer(new StreamConsumer() {
            String buffer;

            @Override
            public void consume(String line) {
                buffer += line + "\n";
            }

            @Override
            public void done() {
                if (buffer != null) {
                    messenger.pushError(buffer);
                }
            }
        });

        try {
            runner.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
