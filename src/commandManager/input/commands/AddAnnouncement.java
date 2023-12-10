package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.content.Announcement;
import data.entities.content.Event;
import data.entities.users.Artist;
import data.entities.users.Host;
import utils.Extras.UserType;

public final class AddAnnouncement implements Command {
    @Override
    public Output action(Input input) {
        Host user = (Host) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.HOST) {
            message = input.getUsername() + " is not an host.";
        } else if (user.findAnnouncement(input.getName()) != null) {
            message = input.getUsername() + " has already added an announcement with this name.";
        } else {
            user.addAnnouncement(new Announcement(input.getName(), input.getDescription()));
            message = input.getUsername() + " has successfully added new announcement.";
        }

        return new Output(input, message);
    }
}
