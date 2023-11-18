package commandManager.input.commandStrategies;

import commandManager.input.Input;
import data.Database;
import data.entities.audio.File;
import data.entities.user.User;
import commandManager.output.Output;

import java.util.ArrayList;

public final class SelectStrategy implements CommandStrategy {
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        int itemNumber = input.getItemNumber();
        ArrayList<File> searchResults = user.getSearchResults();
        String message;

        if (searchResults.isEmpty()) {
            message = "Please conduct a search before making a selection.";
        } else if (itemNumber > searchResults.size()) {
            message = "The selected ID is too high.";
        } else {
            try {
                user.select(searchResults.get(itemNumber - 1));
                message = "Successfully selected " + user.getSelectedFile().getName() + ".";
            } catch (IndexOutOfBoundsException e) {
                message = "The selected ID is too low.";
            }
        }

        return new Output(input, message);
    }
}
