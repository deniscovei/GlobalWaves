package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;

public interface Command {
        /**
         * Executes the action of the command.
         * @param input The input of the command.
         * @return The output of the command.
         */
        Output action(Input input);
}
