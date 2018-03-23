package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarViewPageRequestEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewCalendarByCommand extends Command {

    public static final String COMMAND_WORD = "viewCalendarBy";
    public static final String COMMAND_ALIAS = "vcb";
    public static final String[] VALID_ARGUMENT = {"day", "week", "month", "year"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the calendar view specified.\n"
            + "Parameters: CALENDARVIEWPAGE (must be one of day, week, month, year. Not case-sensitive)\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS = "Opened calendar view: %1$s";

    private final String calendarViewPage;

    public ViewCalendarByCommand(String calendarViewPage) {
        this.calendarViewPage = calendarViewPage;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(calendarViewPage));
        return new CommandResult(String.format(MESSAGE_SUCCESS, calendarViewPage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.calendarViewPage.equals(((ViewCalendarByCommand) other).calendarViewPage)); // state check
    }
}
