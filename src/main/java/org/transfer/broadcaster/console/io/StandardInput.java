package org.transfer.broadcaster.console.io;

import java.util.Scanner;

public class StandardInput implements Input {

    private final String[] arguments;

    /**
     * @param arguments argument array
     */
    public StandardInput(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String getArgument(String name) {
        throw new UnsupportedOperationException("Arguments can only be fetched by index.");
    }

    @Override
    public boolean hasArgument(String name) {
        throw new UnsupportedOperationException("Arguments can only be fetched by index.");
    }

    @Override
    public String getArgument(int index) {
        return arguments[index];
    }

    @Override
    public boolean hasArgument(int index) {
        return arguments.length > index;
    }

    @Override
    public String readLine() {
        return new Scanner(System.in).nextLine();
    }
}
