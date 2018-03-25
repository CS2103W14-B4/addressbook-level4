package systemtests.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MAP_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MAP_ADDRESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.map.EstimateRouteCommand;
import seedu.address.model.Model;
import seedu.address.model.map.MapAddress;
import systemtests.AddressBookSystemTest;

public class EstimateRouteCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void estimateRoute() {
        Model model = getModel();

        /* ------------------------------ Perform valid estimateRoute operations --------------------------------- */

        /* Case: shows best estimated time and distance of travel between two locations
         *  -> Information shown in result display
         */
        MapAddress startAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        MapAddress endAddress = new MapAddress(VALID_ADDRESS_MAP_AMY);
        assertCommandSuccess(startAddress, endAddress);

        /* Case: shows best estimated time and distance of travel between two locations using postal code
         * -> Information shown in result display
         */
        startAddress = new MapAddress("820296");
        endAddress = new MapAddress("119077");
        assertCommandSuccess(startAddress, endAddress);

        /* Case: shows best estimated time and distance of travel between two locations using location name
         * -> Information shown in result display
         */
        startAddress = new MapAddress("National University of Singapore");
        endAddress = new MapAddress("Punggol");
        assertCommandSuccess(startAddress, endAddress);

        /* ------------------------------ Perform invalid estimateRoute operations --------------------------------- */

        /* Case: missing MapAddress and prefix-> rejected */
        String command = EstimateRouteCommand.COMMAND_WORD + "";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EstimateRouteCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "estimateroute " + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB
                + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_AMY;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: correct prefix but missing MapAddress -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS + " " + PREFIX_END_MAP_ADDRESS;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: correct prefix but invalid MapAddress -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS + "))))))" + " "
                + PREFIX_END_MAP_ADDRESS + "^^^^^^^";
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: missing MapAddress prefix -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + VALID_ADDRESS_MAP_BOB + " " + VALID_ADDRESS_MAP_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EstimateRouteCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code EstimateRouteCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code EstimateRouteCommand}.<br>
     * 4. Shows the distance and time of travel in result display.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(MapAddress startAddress, MapAddress endAddress) {
        assertCommandSuccess(EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS
                + startAddress.toString() + " " + PREFIX_END_MAP_ADDRESS + endAddress.toString());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Tag)}. Executes {@code command}
     * instead.
     * @see EstimateRouteCommandSystemTest#assertCommandSuccess(MapAddress, MapAddress)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = EstimateRouteCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see EstimateRouteCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedResultMessage = expectedResultMessage + EstimateRouteCommand.getStringOfDistanceAndTime();
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
