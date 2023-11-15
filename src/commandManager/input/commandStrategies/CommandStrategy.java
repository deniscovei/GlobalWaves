package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import commandManager.output.OutputCommand;

public interface CommandStrategy {
        OutputCommand action(InputCommand inputCommand);
}
