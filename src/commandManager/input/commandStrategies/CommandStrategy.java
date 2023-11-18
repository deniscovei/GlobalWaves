package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.output.Output;

public interface CommandStrategy {
        Output action(Input inputCommand);
}
