package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.audioFiles.Ad;
import data.entities.users.Listener;

public class AdBreak implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (!user.hasLoadedAFile() || user.getPlayer().hasFinished(input.getTimestamp())) {
            message = input.getUsername() + " is not playing any music.";
        } else {
            user.pushAd(new Ad(input.getPrice()));
            message = "Ad inserted successfully.";
        }

        return new Output(input, message);
    }
}
