package commandManager.input.commandStrategies;

import commandManager.input.InputCommand;
import data.Database;
import data.entities.audio.File;
import data.entities.user.User;
import commandManager.output.OutputCommand;
import data.entities.audio.audioFiles.AudioFile;

import java.util.ArrayList;

public final class SelectStrategy implements CommandStrategy {
    public OutputCommand action(InputCommand inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        int itemNumber = inputCommand.getItemNumber();
        ArrayList<File> searchResults = user.getSearchResults();
        String message;

        int searchResultsIndex = itemNumber - 1;
        if (searchResults.isEmpty()) {
            message = "Please conduct a search before making a selection.";
        } else if (searchResultsIndex >= searchResults.size()) {
            message = "The selected ID is too high.";
        } else {
            user.setSelection(searchResults.get(searchResultsIndex));
            message = "Successfully selected " + user.getSelection().getName() + ".";
        }

        return new OutputCommand(inputCommand, message);
    }
}
