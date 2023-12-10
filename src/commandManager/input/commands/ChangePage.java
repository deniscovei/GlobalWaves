package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.Extras.PageType;
import utils.Extras;

public final class ChangePage implements Command {
    @Override
    public Output action(Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            PageType pageType = Extras.searchPage(input.getNextPage());

            if (pageType == null) {
                message = input.getUsername() + " is trying to access a non-existent page.";
            } else {
                user.goToNextPage(pageType);
                message = input.getUsername() + " accessed " + input.getNextPage() + " successfully.";
            }
        }

        return new Output(input, message);
    }
}
