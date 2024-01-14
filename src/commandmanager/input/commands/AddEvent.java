package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.content.Event;
import data.entities.users.contentCreator.Artist;
import data.entities.users.User;
import utils.AppUtils;
import utils.AppUtils.UserType;

public final class AddEvent implements Command {
    @Override
    public Output action(final Input input) {
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
            ((Artist) user).addEvent(new Event(input.getName(), input.getDescription(),
                                               input.getDate()));
            message = input.getUsername() + " has added new event successfully.";
        }

        return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .message(message)
                .build();
    }

    private boolean validDate(final String date) {
        if (date.length() != AppUtils.DATE_LENGTH
            || date.charAt(AppUtils.DAY_END_POS) != '-'
            || date.charAt(AppUtils.MONTH_END_POS) != '-') {
            return false;
        }

        for (int i = 0; i < date.length(); i++) {
            if (i != AppUtils.DAY_END_POS && i != AppUtils.MONTH_END_POS
                && !Character.isDigit(date.charAt(i))) {
                return false;
            }
        }

        int day = Integer.parseInt(date.substring(AppUtils.DAY_START_POS, AppUtils.DAY_END_POS));
        int month = Integer.parseInt(date.substring(AppUtils.MONTH_START_POS,
                                                    AppUtils.MONTH_END_POS));
        int year = Integer.parseInt(date.substring(AppUtils.YEAR_START_POS, AppUtils.YEAR_END_POS));

        return year >= AppUtils.MIN_YEAR && year <= AppUtils.CURR_YEAR
            && month >= 1 && month <= AppUtils.NO_MONTHS
            && (month != AppUtils.FEBRUARY || day <= AppUtils.MIN_NO_DAYS_IN_FEBRUARY)
            && day <= AppUtils.MAX_NO_DAYS_IN_A_MONTH;
    }
}
