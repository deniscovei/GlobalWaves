package commandManager.input.commands;

import commandManager.input.Input;
import data.Database;
import data.entities.Selection;
import data.entities.users.Listener;
import commandManager.output.Output;
import utils.Extras.SearchType;

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
        ArrayList<String> searchResults = Objects.requireNonNull(user).getSearchBar().getSearchResults();
        String message;

        if (!user.havePerformedSearch()) {
            message = "Please conduct a search before making a selection.";
        } else if (itemNumber > searchResults.size()) {
            message = "The selected ID is too high.";
        } else {
            try {
                SearchType searchType = user.getSearchBar().getSearchType();
                Selection selection = new Selection(searchType, searchResults.get(itemNumber - 1));
                user.select(selection);

                message = "Successfully selected " + searchResults.get(itemNumber - 1);
                switch (selection.getSelectionType()) {
                    case FILE -> message += ".";
                    case PAGE -> message += "'s page.";
                }
            } catch (IndexOutOfBoundsException e) {
                message = "The selected ID is too low.";
            }
        }

        return new Output(input, message);
    }
}
