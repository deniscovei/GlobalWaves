package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.content.Announcement;
import data.entities.users.contentCreator.Host;
import utils.AppUtils;

public final class RemoveAnnouncement implements Command {
    @Override
    public Output action(final Input input) {
        Host user = (Host) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != AppUtils.UserType.HOST) {
            message = input.getUsername() + " is not an host.";
        } else {
            Announcement event = user.findAnnouncement(input.getName());
            if (event == null) {
                message = input.getUsername() + " has no announcement with the given name.";
            } else {
                user.removeAnnouncement(event);
                message = input.getUsername() + " has successfully deleted the announcement.";
            }
        }

        return new Output(input, message);
    }
}
