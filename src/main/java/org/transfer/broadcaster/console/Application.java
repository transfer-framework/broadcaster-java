package org.transfer.broadcaster.console;

import org.transfer.broadcaster.console.io.Input;
import org.transfer.broadcaster.console.io.Output;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private String name;
    private String version;
    private String programName;

    private HashMap<String, Command> commands = new HashMap<>();

    private Input input;
    private Output output;

    public Application(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Adds a command to application.
     *
     * @param command Command
     */
    public void addCommand(Command command) {
        if (command.getName() == null) {
            throw new IllegalArgumentException("Command name can not be null");
        }

        commands.put(command.getName(), command);
    }

    /**
     * Starts the application.
     */
    public void run() {

        if (input.hasArgument(0)) {
            Command command = commands.get(input.getArgument(0));

            if (command == null) {
                output.writeln(AnsiColors.ANSI_RED + "Command not found." + AnsiColors.ANSI_RESET);

                renderHelpMessage();

                return;
            }

            command.execute(input, output);
        }
        else {
            renderHelpMessage();
        }
    }

    /**
     * Executes command.
     *
     * @param command command
     */
    private void execute(Command command) {
        command.execute(input, output);
    }

    /**
     * Renders help message.
     */
    private void renderHelpMessage() {
        output.writeln(name + " " + version + "\n");
        output.writeln(AnsiColors.ANSI_YELLOW + "Usage:" + AnsiColors.ANSI_RESET + " \n " + programName + " [command]\n");
        output.writeln(AnsiColors.ANSI_YELLOW + "Arguments: "+ AnsiColors.ANSI_RESET);
        output.writeln(" " + AnsiColors.ANSI_GREEN + "command" + AnsiColors.ANSI_RESET + "\t\tThe command to execute\n");
        output.writeln(AnsiColors.ANSI_YELLOW + "Available commands: "+ AnsiColors.ANSI_RESET);

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            output.writeln(" " + AnsiColors.ANSI_GREEN + entry.getKey() + AnsiColors.ANSI_RESET + "\t\t\t" +
                    entry.getValue().getDescription());
        }
    }

}
