package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;
import utils.AppUtils.PageType;
import utils.AppUtils;

public final class ChangePage implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            PageType pageType = AppUtils.searchPage(input.getNextPage());

            if (pageType == null) {
                message = input.getUsername() + " is trying to access a non-existent page.";
            } else {
                user.changePage(pageType, input.getTimestamp());
                message = input.getUsername() + " accessed " + input.getNextPage()
                    + " successfully.";
            }
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
