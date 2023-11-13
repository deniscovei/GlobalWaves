package commands.derived.input.commandTypes;

import commands.derived.input.inputCommand.InputCommand;
import data.database.Database;
import data.entities.user.User;
import commands.derived.output.OutputCommand;
import data.entities.audio.base.AudioFile;
import lombok.Getter;

import java.util.ArrayList;

public final class Select {
    public static OutputCommand action(InputCommand inputCommand, int itemNumber) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        ArrayList<AudioFile> searchResults = user.getSearchResults();
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
