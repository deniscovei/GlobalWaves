package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.content.Event;
import data.entities.users.Artist;
import utils.Extras.UserType;

public final class RemoveEvent implements Command {
    @Override
    public Output action(Input input) {
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

        return new Output(input, message);
    }
}
