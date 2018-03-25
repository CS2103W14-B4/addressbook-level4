package seedu.address.logic.parser.map;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MAP_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MAP_ADDRESS;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.map.EstimateRouteCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.map.MapAddress;

/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class EstimateRouteCommandParser implements Parser<EstimateRouteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EstimateRouteCommand
     * and returns an EstimateRouteCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public EstimateRouteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_MAP_ADDRESS, PREFIX_END_MAP_ADDRESS);
        if (!arePrefixesPresent(argMultiMap, PREFIX_START_MAP_ADDRESS, PREFIX_END_MAP_ADDRESS)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EstimateRouteCommand.MESSAGE_USAGE));
        }

        try {
            MapAddress start = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_START_MAP_ADDRESS)).get();
            MapAddress end = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_END_MAP_ADDRESS)).get();
            if (!MapAddress.isValidAddressForEstimatingRoute(start.toString(), end.toString())) {
                throw new InvalidAddress("");
            }
            return new EstimateRouteCommand(start, end);
        } catch (IllegalValueException ive) {
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        } catch (InvalidAddress ia) {
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS_ROUTE_ESTIMATION);
        }
    }
}
