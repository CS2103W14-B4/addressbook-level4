package seedu.address.logic.commands.map;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_ADDRESS;

import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.map.Geocoding;
import seedu.address.model.map.Map;
import seedu.address.model.map.MapAddress;

/**
 * Update the Map by adding a marker to the location of map address
 * and delete existing marker if it exist
 */
public class ShowLocationCommand extends Command {

    public static final String COMMAND_WORD = "showLocation";
    public static final String COMMAND_ALIAS = "sl";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the location of address in the Map.\n"
            + "Parameters: "
            + PREFIX_MAP_ADDRESS + "LOCATION (Name of location or postal code)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_ADDRESS + "Punggol Central\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_ADDRESS + "820296";

    public static final String MESSAGE_SUCCESS = "Location is being shown in Map (identified by marker)!";

    private final MapAddress address;

    /**
     * Creates an AddAppointmentCommand with the following parameters
     * @param address The created appointment
     */
    public ShowLocationCommand (MapAddress address) {
        requireNonNull(address);
        this.address = address;
        Map.removeExistingMarker();
        Map.clearRoute();
    }

    @Override
    public CommandResult execute() {
        addNewMarkerToMap();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowLocationCommand // instanceof handles nulls
                && this.address.equals(((ShowLocationCommand) other).address));
    }

    /**
     * Remove any existing marker and adds new marker {@code location} to Map
     */
    public void addNewMarkerToMap() {

        LatLong center = getLatLong();

        Map.setLocation(getMarkerOptions(center));

        Map.setMarkerOnMap(center);
    }

    private Marker getMarkerOptions(LatLong center) {
        MarkerOptions markOptions = new MarkerOptions();
        markOptions.animation(Animation.DROP)
                .position(center)
                .visible(true);
        return new Marker(markOptions);
    }

    private LatLong getLatLong() {
        Geocoding convertToLatLng = new Geocoding();
        convertToLatLng.initialiseLatLngFromAddress(address.toString());
        return new LatLong(convertToLatLng.getLat(), convertToLatLng.getLong());
    }
}