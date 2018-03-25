package seedu.address.logic.parser.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MAP_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MAP_ADDRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.map.EstimateRouteCommand;
import seedu.address.model.map.MapAddress;

public class EstimateRouteCommandParserTest {
    private EstimateRouteCommandParser parser = new EstimateRouteCommandParser();

    @Test
    public void parse_validArgs_returnsEstimateRouteCommand() {
        MapAddress startAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        MapAddress endAddress = new MapAddress(VALID_ADDRESS_MAP_AMY);
        assertParseSuccess(parser, " " + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB + " "
                + PREFIX_END_MAP_ADDRESS + VALID_ADDRESS_MAP_AMY, new EstimateRouteCommand(startAddress, endAddress));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EstimateRouteCommand.MESSAGE_USAGE));
    }

}
