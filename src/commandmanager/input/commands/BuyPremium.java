package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Listener;

public final class BuyPremium implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.isPremium()) {
            message = input.getUsername() + " is already a premium user.";
        } else {
            user.buyPremiumSubscription();
            message = input.getUsername() + " bought the subscription successfully.";
        }

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message(message)
            .build();
    }
}
