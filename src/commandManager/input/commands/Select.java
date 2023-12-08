package commandManager.input.commands;

import commandManager.input.Input;
import data.Database;
import data.entities.audio.File;
import data.entities.user.Listener;
import commandManager.output.Output;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implements the command strategy for selecting a file.
 */
public final class Select implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.");
        }

        int itemNumber = input.getItemNumber();
        ArrayList<File> searchResults = Objects.requireNonNull(user).getSearchResults();
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
