package CommandTypes.input;

import fileio.input.LibraryInput;
import CommandTypes.output.OutputCommand;
import lombok.Getter;

@Getter
public final class Select {
    static String selection;

    public static void setSelection(String selection) {
        Select.selection = selection;
    }

    public static OutputCommand action(InputCommand inputCommand, Integer itemNumber, LibraryInput library) {
        OutputCommand outputCommand = new OutputCommand(inputCommand.getCommand(), inputCommand.getUsername(),
                                                        inputCommand.getTimestamp(), true, false);
        Integer searchResultsIndex = itemNumber - 1;
        if (Search.getResults().isEmpty()) {
            outputCommand.setMessage("Please conduct a search before making a selection.");
            return outputCommand;
        } else if (searchResultsIndex >= Search.getResults().size()) {
            outputCommand.setMessage("The selected ID is too high.");
            return outputCommand;
        } else {
            selection = Search.getResults().get(searchResultsIndex);
            outputCommand.setMessage("Successfully selected " + selection + ".");
            return outputCommand;
        }
    }
}
