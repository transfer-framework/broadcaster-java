package org.transfer.agent.console;

import org.transfer.agent.console.io.Input;
import org.transfer.agent.console.io.Output;

/**
 * Console command
 */
public interface Command {

    /**
     * Returns command name
     *
     * @return name
     */
    String getName();

    /**
     * Returns description
     *
     * @return description
     */
    String getDescription();

    /**
     * Executes command
     *
     * @param input input interface
     * @param output output interface
     */
    void execute(Input input, Output output);
}
