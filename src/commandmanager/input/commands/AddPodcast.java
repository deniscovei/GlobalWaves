package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.audio.audioCollections.Podcast;
import data.entities.users.Host;
import data.entities.users.User;
import fileio.input.EpisodeInput;
import utils.AppUtils;
import utils.AppUtils.UserType;

public final class AddPodcast implements Command {
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

            if (host.findPodcast(input.getName()) != null) {
                message = input.getUsername() + " has another podcast with the same name.";
            } else if (AppUtils.hasDuplicates(input.getEpisodes(), EpisodeInput::getName)) {
                message = input.getUsername() + " has the same episode in this podcast.";
            } else {
                Podcast newPodcast = new Podcast(input.getName(), input.getUsername(),
                                                 input.getEpisodes());
                host.addPodcast(newPodcast);
                Database.getInstance().getPodcasts().add(newPodcast);

                message = input.getUsername() + " has added new podcast successfully.";
            }
        }

        return new Output(input, message);
    }
}
