package seedu.address.logic.parser.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAP_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_ADDRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.map.ShowLocationCommand;
import seedu.address.model.map.MapAddress;

public class ShowLocationCommandParserTest {

    private ShowLocationCommandParser parser = new ShowLocationCommandParser();

    @Test
    public void parse_validArgs_returnsShowLocationCommand() {
        MapAddress address = new MapAddress(VALID_ADDRESS_MAP_BOB);
        assertParseSuccess(parser, " " + PREFIX_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB, new ShowLocationCommand(address));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));
    }
}
