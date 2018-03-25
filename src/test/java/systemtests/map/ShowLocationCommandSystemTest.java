package systemtests.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_MAP_DESC1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_MAP_DESC2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_ADDRESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.map.ShowLocationCommand;
import seedu.address.model.Model;
import seedu.address.model.map.MapAddress;
import systemtests.AddressBookSystemTest;

public class ShowLocationCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void showLocation() {

        /* ------------------------------ Perform valid showLocation operations --------------------------------- */

        /* Case: show location using address (block and street name) of a place
         * -> location marker shown in map
         */
        MapAddress newAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        assertCommandSuccess(newAddress);

        /* Case: show location using postal code of a place
         * -> location marker shown in map
         */
        newAddress = new MapAddress("820296");
        assertCommandSuccess(newAddress);

        /* Case: show location using name of a place (e.g National University of Singapore)
         * -> location marker shown in map
         */
        newAddress = new MapAddress("National University of Singapore");
        assertCommandSuccess(newAddress);

        /* ----------------------------------- Perform invalid removeTag operations --------------------------------- */

        /* Case: missing MapAddress and prefix-> rejected */
        String command = ShowLocationCommand.COMMAND_WORD + "";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "showslocation " + VALID_ADDRESS_MAP_BOB;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: correct prefix but missing MapAddress -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + INVALID_ADDRESS_MAP_DESC2;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: correct prefix but invalid MapAddress -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + INVALID_ADDRESS_MAP_DESC1;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: missing MapAddress prefix -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + " " + VALID_ADDRESS_MAP_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code ShowLocationCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ShowLocationCommand}.<br>
     * 4. Shows the location marker of {@code address} in Maps GUI.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(MapAddress address) {
        assertCommandSuccess(ShowLocationCommand.COMMAND_WORD + " " + PREFIX_MAP_ADDRESS + address.toString());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(MapAddress)}. Executes {@code command}
     * instead.
     * @see ShowLocationCommandSystemTest#assertCommandSuccess(MapAddress)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = ShowLocationCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ShowLocationCommandSystemTest#assertCommandSuccess(String)
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
}
