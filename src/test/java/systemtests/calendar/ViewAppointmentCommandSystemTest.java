package systemtests.calendar;

import static seedu.address.logic.commands.CommandTestUtil.APPT_END_DATE_DESC_GRAMMY;
import static seedu.address.logic.commands.CommandTestUtil.APPT_END_DATE_DESC_OSCAR;
import static seedu.address.logic.commands.CommandTestUtil.APPT_END_TIME_DESC_GRAMMY;
import static seedu.address.logic.commands.CommandTestUtil.APPT_END_TIME_DESC_OSCAR;
import static seedu.address.logic.commands.CommandTestUtil.APPT_LOCATION_DESC_OSCAR;
import static seedu.address.logic.commands.CommandTestUtil.APPT_NAME_DESC_GRAMMY;
import static seedu.address.logic.commands.CommandTestUtil.APPT_NAME_DESC_OSCAR;
import static seedu.address.logic.commands.CommandTestUtil.APPT_START_DATE_DESC_GRAMMY;
import static seedu.address.logic.commands.CommandTestUtil.APPT_START_DATE_DESC_OSCAR;
import static seedu.address.logic.commands.CommandTestUtil.APPT_START_TIME_DESC_GRAMMY;
import static seedu.address.logic.commands.CommandTestUtil.APPT_START_TIME_DESC_OSCAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINT_OF_CONTACT;
import static seedu.address.testutil.TestUtil.getCelebrityIndices;
import static seedu.address.testutil.TestUtil.getPersonIndices;
import static seedu.address.testutil.TypicalCelebrities.JAY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalStorageCalendar.GRAMMY;
import static seedu.address.testutil.TypicalStorageCalendar.OSCAR;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.calendar.AddAppointmentCommand;
import seedu.address.logic.commands.calendar.ViewAppointmentCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Celebrity;
import seedu.address.model.person.Person;
import systemtests.AddressBookSystemTest;
//@@author Damienskt
public class ViewAppointmentCommandSystemTest extends AddressBookSystemTest {

    private List<Celebrity> celebrityList = new ArrayList<>();
    private List<Index> celebrityIndices = new ArrayList<>();
    private List<Person> pointOfContactList = new ArrayList<>();
    private List<Index> pointOfContactIndices = new ArrayList<>();

    @Test
    public void viewAppointment() {
        /**
         * Pre-populate application with appointments
         * Appointment without location, celebrities and points of contact
         */
        String command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_GRAMMY + APPT_END_DATE_DESC_GRAMMY
                + APPT_END_TIME_DESC_GRAMMY + APPT_START_DATE_DESC_GRAMMY
                + APPT_START_TIME_DESC_GRAMMY;
        executeCommand(command);

        /* Appointment with location, celebrities and points of contact */
        pointOfContactList.add(BENSON);
        celebrityList.add(JAY);
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        pointOfContactIndices.addAll(getPersonIndices(this.getModel(), pointOfContactList));
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_END_DATE_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);
        executeCommand(command);

        /* ---------------------------- Perform invalid viewAppointment operations --------------------------------- */
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " " + 0, ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " ads" , ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " ", ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " "
                + 2 , ViewAppointmentCommand.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);

        executeCommand("listAppointment"); // Executes listAppointment to fulfil pre-requisite
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " "
                + 10, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);

        /* ------------------------------ Perform valid viewAppointment operations --------------------------------- */
        assertCommandSuccess(1); //viewing appointment without location, celebrities and points of contact
        assertCommandSuccess(2); //viewing appointment with location, celebrities and points of contact
    }

    /**
     * Executes the {@code ViewAppointmentCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ViewAppointmentCommand}.<br>
     * 4. Shows the location marker of appointment location in Maps GUI.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(int index) {
        assertCommandSuccess(ViewAppointmentCommand.COMMAND_WORD + " " + index, index);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(int)}. Executes {@code command}
     * instead.
     * @see ViewAppointmentCommandSystemTest#assertCommandSuccess(int)
     */
    private void assertCommandSuccess(String command, int index) {
        Model expectedModel = getModel();
        Appointment selected;
        String location;
        if (index == 2) {
            selected = GRAMMY;
            location = ViewAppointmentCommand.MESSAGE_NO_LOCATION;
        } else {
            selected = OSCAR;
            selected.updateEntries(celebrityList, pointOfContactList);
            location = selected.getMapAddress().toString();
        }
        String expectedResultMessage = "Selected appointment details:\n"
                + "Appointment Name: " + selected.getTitle() + "\n"
                + "Start Date: " + selected.getStartDate() + "\n"
                + "Start Time: " + selected.getStartTime() + "\n"
                + "End Date: " + selected.getEndDate() + "\n"
                + "End Time: " + selected.getEndTime() + "\n"
                + "Location: " + location + "\n"
                + "Celebrities attending: " + selected.getCelebritiesAttending() + "\n"
                + "Points of Contact: " + selected.getPointsOfContact();

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String,int)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ViewAppointmentCommandSystemTest#assertCommandSuccess(String,int)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates command string for a list of celebrities and POCs for use with add Appointment command
     */
    private String generatePointOfContactandCelebrityFields(List<Index> celebrityIndices,
                                                            List<Index> pointOfContactIndices) {
        return " " + generateCelebrityFields(celebrityIndices) + " "
                + generatePointOfContactFields(pointOfContactIndices);
    }

    /**
     * Generates a command string for a list of celebrity indices for add Appointment command
     */
    private String generateCelebrityFields(List<Index> celebrityIndices) {
        StringBuilder sb =  new StringBuilder();
        for (Index i : celebrityIndices) {
            sb.append(PREFIX_CELEBRITY).append(i.getOneBased() + " ");
        }
        return sb.toString();
    }

    /**
     * Generates a command string for a list of POC indices for add Appointment command
     */
    private String generatePointOfContactFields(List<Index> pointOfContactIndices) {
        StringBuilder sb =  new StringBuilder();
        for (Index i : pointOfContactIndices) {
            sb.append(PREFIX_POINT_OF_CONTACT).append(i.getOneBased() + " ");
        }
        return sb.toString();
    }
}
