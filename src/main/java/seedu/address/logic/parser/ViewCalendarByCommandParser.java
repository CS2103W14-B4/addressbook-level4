package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ViewCalendarByCommand.VALID_ARGUMENT;

import seedu.address.logic.commands.ViewCalendarByCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ViewCalendarByCommand object
 */
public class ViewCalendarByCommandParser implements Parser<ViewCalendarByCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCalendarByCommand
     * and returns a ViewCalendarByCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCalendarByCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] arguments = trimmedArgs.split("\\s+");
        if (!isValidArgument(arguments)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarByCommand.MESSAGE_USAGE));
        }

        return new ViewCalendarByCommand(arguments[0].toLowerCase());
    }

    /**
     * Takes in {@code String[]} of arguments
     * @returns true if the argument is valid, ie: only 1 argument, and argument is one of {day, week, month, year}
     */
    private boolean isValidArgument(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        String argument = arguments[0];
        for (String validArgument: VALID_ARGUMENT) {
            if (validArgument.equalsIgnoreCase(argument)) {
                return true;
            }
        }
        return false;
    }
}
