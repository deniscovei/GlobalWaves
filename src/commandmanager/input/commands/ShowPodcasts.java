package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Host;
import fileio.output.PodcastOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShowPodcasts implements Command {
    @Override
    public Output action(final Input input) {
        Host host = (Host) Database.getInstance().findUser(input.getUsername());

        List<PodcastOutput> result = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(host).getPodcasts().size(); i++) {
            result.add(new PodcastOutput(host, i));
        }

        return new Output(input, result, null);
    }
}
