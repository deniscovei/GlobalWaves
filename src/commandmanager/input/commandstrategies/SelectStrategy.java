package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import data.Database;
import data.entities.audio.File;
import data.entities.user.User;
import commandmanager.output.Output;

import java.util.ArrayList;

/**
 * This class implements the command strategy for selecting a file.
 */
public final class SelectStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        int itemNumber = input.getItemNumber();
        ArrayList<File> searchResults = user.getSearchResults();
        String message;

        if (!user.havePerformedSearch()) {
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
