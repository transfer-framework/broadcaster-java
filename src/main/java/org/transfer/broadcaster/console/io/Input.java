package org.transfer.broadcaster.console.io;

/**
 * Input interface
 */
public interface Input {

    /**
     * Returns argument
     *
     * @param  name argument name
     * @return argument value
     */
    String getArgument(String name);

    /**
     * Checks if argument exists
     *
     * @param  name argument name
     * @return true, if argument exists
     */
    boolean hasArgument(String name);

    /**
     * Returns argument
     *
     * @param  index argument index
     * @return argument value
     */
    String getArgument(int index);

    /**
     * Checks if argument exists
     *
     * @param  index argument index
     * @return true, if argument exists
     */
    boolean hasArgument(int index);

    /**
     * Reads a line from client
     *
     * @return line read
     */
    String readLine();
}
