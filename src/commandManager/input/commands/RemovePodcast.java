package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioCollections.Podcast;
import data.entities.users.Artist;
import data.entities.users.Host;
import data.entities.users.User;
import utils.Extras.UserType;

public final class RemovePodcast implements Command {
    @Override
    public Output action(Input input) {
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
                Database.getInstance().removePodcast(podcast);
                message = input.getUsername() + " deleted the podcast successfully.";
            }
        }

        return new Output(input, message);
    }
}


