package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioCollections.Podcast;
import data.entities.users.contentCreator.Host;
import data.entities.users.User;
import utils.AppUtils.UserType;

public final class RemovePodcast implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.HOST) {
            message = input.getUsername() + " is not a host.";
        } else {
            Host host = (Host) user;
            Podcast podcast = host.findPodcast(input.getName());

            if (podcast == null) {
                message = input.getUsername() + " doesn't have a podcast with the given name.";
            } else if (podcast.interactingWithOthers(input.getTimestamp())) {
                message = input.getUsername() + " can't delete this podcast.";
            } else {
                host.removePodcast(podcast);
                message = input.getUsername() + " deleted the podcast successfully.";
            }
        }

        return new Output(input, message);
    }
}


