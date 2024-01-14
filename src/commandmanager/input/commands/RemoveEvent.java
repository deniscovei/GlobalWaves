package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.content.Event;
import data.entities.users.contentCreator.Artist;
import utils.AppUtils.UserType;

public final class RemoveEvent implements Command {
    @Override
    public Output action(final Input input) {
        Artist user = (Artist) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else {
            Event event = user.findEvent(input.getName());
            if (event == null) {
                message = input.getUsername() + " doesn't have an event with the given name.";
            } else {
                user.removeEvent(event);
                message = input.getUsername() + " deleted the event successfully.";
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
