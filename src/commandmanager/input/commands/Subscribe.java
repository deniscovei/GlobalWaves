package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.contentCreator.ContentCreator;
import data.entities.users.Listener;
import utils.AppUtils;

public final class Subscribe implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getCurrentPage().getPageType() != AppUtils.PageType.ARTIST_PAGE
            && user.getCurrentPage().getPageType() != AppUtils.PageType.HOST_PAGE) {
            message = "To subscribe you need to be on the page of an artist or host.";
        } else {
            ContentCreator pageCreator = (ContentCreator) user.getCurrentPage().getCreator();
            message = input.getUsername();

            if (!pageCreator.getSubscribers().contains(user)) {
                user.subscribe(pageCreator);
                pageCreator.registerSubscribe(user);
                message += " subscribed to ";
            } else {
                user.unsubscribe(pageCreator);
                pageCreator.registerUnsubscribe(user);
                message += " unsubscribed from ";
            }

            message += pageCreator.getUsername() + " successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
