package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.output.Output;

public interface CommandStrategy {
        /**
         * Executes the action of the command.
         * @param input The input of the command.
         * @return The output of the command.
         */
        Output action(Input input);
}
