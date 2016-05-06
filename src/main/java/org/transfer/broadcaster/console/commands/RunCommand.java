package org.transfer.broadcaster.console.commands;

import org.transfer.broadcaster.console.Command;
import org.transfer.broadcaster.console.io.Input;
import org.transfer.broadcaster.console.io.Output;
import org.transfer.broadcaster.messenger.ActivityMessenger;
import org.transfer.broadcaster.meteor.Client;
import org.transfer.broadcaster.meteor.DefaultClient;
import org.transfer.broadcaster.models.TransferProcess;
import org.transfer.broadcaster.system.ProcessRunner;
import org.transfer.broadcaster.system.StreamConsumer;

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
