package org.transfer.agent.console.io;

public class StandardOutput implements Output {

    public void write(String string) {
        System.out.print(string);
    }

    public void writeln(String string) {
        System.out.println(string);
    }
}
