package seedu.address.logic.commands.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.map.MapAddress;
/**
 * Contains integration tests and unit tests for
 * {@code EstimateRouteCommand}.
 */
public class EstimateRouteCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EstimateRouteCommand(null, null);
    }

    @Test
    public void equals() {

        EstimateRouteCommand estimateRouteFirstCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB),
                new MapAddress(VALID_ADDRESS_MAP_AMY));
        EstimateRouteCommand estimateRouteSecondCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_AMY),
                new MapAddress(VALID_ADDRESS_MAP_BOB));

        // same object -> returns true
        assertTrue(estimateRouteFirstCommand.equals(estimateRouteFirstCommand));

        // same tag -> returns true
        EstimateRouteCommand estimateRouteFirstCommandCopy = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB),
                new MapAddress(VALID_ADDRESS_MAP_AMY));
        assertTrue(estimateRouteFirstCommand.equals(estimateRouteFirstCommandCopy));

        // different types -> returns false
        assertFalse(estimateRouteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(estimateRouteFirstCommand.equals(null));

        // different tags -> returns false
        assertFalse(estimateRouteFirstCommand.equals(estimateRouteSecondCommand));
    }

    /**
     * Returns a {@code estimateRouteCommand} with the parameter {@code start} and {@code end}.
     */
    private EstimateRouteCommand prepareCommand(MapAddress start, MapAddress end) {
        EstimateRouteCommand estimateRouteCommand = new EstimateRouteCommand(start, end);
        return estimateRouteCommand;
    }
}
