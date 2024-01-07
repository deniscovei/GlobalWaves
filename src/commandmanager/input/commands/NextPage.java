package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

public class NextPage implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user.goToNextPage()) {
            message = "The user " + input.getUsername()
                + " has navigated successfully to the next page.";
        } else {
            message = "There are no pages left to go forward.";
        }

        return new Output(input, message);
    }
}
