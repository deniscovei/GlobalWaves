package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.content.Merchandise;
import data.entities.users.contentCreator.Artist;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;

public final class BuyMerch implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else {
            Merchandise merch = Database.getInstance().findMerchandise(user.getCurrentPage()
                    .getCreator(), input.getName());
            User pageCreator = user.getCurrentPage().getCreator();

            if (pageCreator.getUserType() != AppUtils.UserType.ARTIST) {
                message = "Cannot buy merch from this page.";
            } else if (merch == null) {
                message = "The merch " + input.getName() + " doesn't exist.";
            } else {
                user.buyMerch((Artist) pageCreator, merch);
                message = input.getUsername() + " has added new merch successfully.";
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
