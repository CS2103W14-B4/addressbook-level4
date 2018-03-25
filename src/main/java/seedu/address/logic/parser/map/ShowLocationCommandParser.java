package seedu.address.logic.parser.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_ADDRESS;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.map.ShowLocationCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.map.MapAddress;

/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class ShowLocationCommandParser implements Parser<ShowLocationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShowLocationCommand
     * and returns an ShowLocationCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ShowLocationCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_MAP_ADDRESS);
        if (!arePrefixesPresent(argMultiMap, PREFIX_MAP_ADDRESS)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowLocationCommand.MESSAGE_USAGE));
        }

        try {
            MapAddress address = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_MAP_ADDRESS)).get();
            return new ShowLocationCommand(address);
        } catch (IllegalValueException ive) {
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        }
    }
}
