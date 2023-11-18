package commandManager.input.commandStrategies;

import commandManager.input.Input;
import data.Database;
import data.entities.audio.File;
import data.entities.user.User;
import commandManager.output.Output;

import java.util.ArrayList;

public final class SelectStrategy implements CommandStrategy {
    public Output action(Input inputCommand) {
        User user = Database.getInstance().findUser(inputCommand.getUsername());
        int itemNumber = inputCommand.getItemNumber();
        ArrayList<File> searchResults = user.getSearchResults();
        String message;

        if (searchResults.isEmpty()) {
            message = "Please conduct a search before making a selection.";
        } else if (itemNumber > searchResults.size()) {
            message = "The selected ID is too high.";
        } else {
            try {
                user.select(searchResults.get(itemNumber - 1));
                message = "Successfully selected " + user.getSelection().getName() + ".";
            } catch (IndexOutOfBoundsException e) {
                message = "The selected ID is too low.";
            }
        }

        return new Output(inputCommand, message);
    }
}
