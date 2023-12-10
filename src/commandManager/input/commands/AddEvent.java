package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.output.Output;
import data.Database;
import data.entities.content.Event;
import data.entities.users.Artist;
import data.entities.users.User;
import utils.Extras.UserType;

public final class AddEvent implements Command {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        String message;

        if (user == null) {
            message = "The username " + input.getUsername() + " doesn't exist.";
        } else if (user.getUserType() != UserType.ARTIST) {
            message = input.getUsername() + " is not an artist.";
        } else if (((Artist) user).findEvent(input.getName()) != null) {
            message = input.getUsername() + " has another event with the same name.";
        } else if (!validDate(input.getDate())) {
            message = "Event for " + input.getUsername() + " does not have a valid date.";
        } else {
            ((Artist) user).addEvent(new Event(input.getName(), input.getDescription(), input.getDate()));
            message = input.getUsername() + " has added new event successfully.";
        }

        return new Output(input, message);
    }

    private boolean validDate(final String date) {
        if (date.length() != 10 || date.charAt(2) != '-' || date.charAt(5) != '-') {
            return false;
        }

        for (int i = 0; i < date.length(); i++) {
            if (i != 2 && i != 5 && !Character.isDigit(date.charAt(i))) {
                return false;
            }
        }

        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));

        // simplified verification for february
        if (year < 1900 || year > 2023
                || month < 1 || month > 12
                || (month == 2 && day > 28) || day > 31) {
            return false;
        }

        return true;
    }
}
