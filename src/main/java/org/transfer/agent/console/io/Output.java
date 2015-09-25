package org.transfer.agent.console.io;

/**
 * Output interface
 */
public interface Output {

    /**
     * Writes a string to output
     *
     * @param string string to output
     */
    void write(String string);

    /**
     * Writes a string terminated with a new line character to output
     *
     * @param string string to output
     */
    void writeln(String string);
}
