package seedu.address.logic.commands.map;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MAP_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MAP_ADDRESS;
import static seedu.address.ui.MapPanel.getMap;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.Marker;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.map.DistanceEstimate;
import seedu.address.logic.map.Geocoding;
import seedu.address.model.map.MapAddress;

/**
 * Estimates the distance and travel time required between two location
 */
public class EstimateRouteCommand extends Command {

    public static final String COMMAND_WORD = "estimateRoute";
    public static final String COMMAND_ALIAS = "er";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates estimated distance and time of travel of two locations.\n"
            + "Parameters: "
            + PREFIX_START_MAP_ADDRESS + "START LOCATION (Name of location or postal code) "
            + PREFIX_END_MAP_ADDRESS + "END LOCATION (Name of location or postal code)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_MAP_ADDRESS + "Punggol Central "
            + PREFIX_END_MAP_ADDRESS + "NUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_MAP_ADDRESS + "820296 "
            + PREFIX_END_MAP_ADDRESS + "118420";

    public static final String MESSAGE_SUCCESS = "Mode of transport: DRIVING\n";

    private static MapAddress startLocation = null;
    private static MapAddress endLocation = null;
    private static String distOfTravel;
    private static String timeOfTravel;
    private final LatLng startLatLng;
    private final LatLng endLatLng;
    private final GoogleMap map;

    /**
     * Initialises the different class attributes of EstimateRouteCommand
     * @param start
     * @param end
     */
    public EstimateRouteCommand(MapAddress start, MapAddress end) {
        requireNonNull(start);
        requireNonNull(end);
        this.startLocation = start;
        this.endLocation = end;
        this.startLatLng = getLatLong(startLocation);
        this.endLatLng = getLatLong(endLocation);
        map = getMap();
        removeExistingMarker();
    }

    @Override
    public CommandResult execute() {
        setDistAndTimeOfTravel();
        return new CommandResult(MESSAGE_SUCCESS + getStringOfDistanceAndTime());
    }

    /**
     * Retrieves information of {@startLocation}, {@endLocation}, {@distOfTravel} and {@code timeOfTravel}
     * which is then converted to string format to be shown to user
     * @return information of distance and time of travel
     */
    public static String getStringOfDistanceAndTime() {
        return "Start Location: " + startLocation.toString() + "\n"
                + "End Location: " + endLocation.toString() + "\n"
                + "Estimated Distance of travel: " + distOfTravel + "\n"
                + "Estimated Time of travel: " + timeOfTravel;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) // short circuit if same object
                || (other instanceof EstimateRouteCommand // instanceof handles nulls
                && this.startLatLng.toString().equals(((EstimateRouteCommand) other).startLatLng.toString())
                && this.endLatLng.toString().equals(((EstimateRouteCommand) other).endLatLng.toString()));
    }

    /**
     * Remove any existing marker {@code location} in Map
     */
    private void removeExistingMarker() {
        Marker location = ShowLocationCommand.getCurrentMarker();
        if (location != null) {
            map.removeMarker(location);
        }
    }

    /**
     * Calculates {@code distOfTravel} and {@code timeOfTravel}
     */
    private void setDistAndTimeOfTravel() {
        DistanceEstimate distEstimate = new DistanceEstimate();
        distEstimate.calculateDistanceMatrix(startLatLng, endLatLng, TravelMode.DRIVING);
        distOfTravel = distEstimate.getDistOriginDest();
        timeOfTravel = distEstimate.getTravelTime();
    }

    /**
     * Converts {@code address} into LatLng form
     */
    private LatLng getLatLong(MapAddress address) {
        Geocoding convertToLatLng = new Geocoding();
        convertToLatLng.initialiseLatLngFromAddress(address.toString());
        return convertToLatLng.getLatLng();
    }
}
