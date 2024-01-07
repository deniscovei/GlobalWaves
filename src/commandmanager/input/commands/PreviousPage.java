package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

public class PreviousPage implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user.goToPreviousPage()) {
            message = "The user " + input.getUsername()
                + " has navigated successfully to the previous page.";
        } else {
            message = "There are no pages left to go back.";
        }

        return new Output(input, message);
    }
}
