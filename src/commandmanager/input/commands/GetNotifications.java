package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.Notification;
import data.entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class GetNotifications implements Command {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        List<Notification> notifications = new ArrayList<>(user.getNotifications());
        user.clearNotifications();

        return new Output(input, notifications, null);
    }
}
