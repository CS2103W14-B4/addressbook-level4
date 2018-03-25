package seedu.address.model.map;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import seedu.address.logic.map.DistanceEstimate;
import seedu.address.logic.map.Geocoding;

/**
 * Represents a map address in the CelebManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class MapAddress {
    public static final String MESSAGE_ADDRESS_MAP_CONSTRAINTS =
            "Address should be in location name, road name, block and road name or postal code format.\n"
                    + "Note:(Person address may not be valid as it consist of too many details like unit number)";
    public static final String MESSAGE_ADDRESS_MAP_CONSTRAINTS_ROUTE_ESTIMATION =
            "Both address cannot be reached by Driving!\n"
            + "Tips: \n"
            + "1.Be more specific with location name.\n"
            + "2.Insert Country name in front of location name (e.g Botanic Gardens -> Singapore Botanic Gardens).\n"
            + "3.Use Postal Code instead.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_MAP_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid MapAddress.
     */
    public MapAddress(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid map address.
     */
    public static boolean isValidAddress(String test) {
        boolean isValid;
        Geocoding testAddress = new Geocoding();
        isValid = testAddress.checkIfAddressCanBeFound(test);
        return test.matches(ADDRESS_MAP_VALIDATION_REGEX) && isValid;
    }
    /**
     * Returns true if a given string is a valid map address.
     */
    public static boolean isValidAddressForEstimatingRoute(String start, String end) {
        boolean isValid = true;
        LatLng startLatLng;
        LatLng endLatLng;
        DistanceEstimate checkValid = new DistanceEstimate();
        Geocoding latLong = new Geocoding();
        latLong.initialiseLatLngFromAddress(start);
        startLatLng = latLong.getLatLng();
        latLong.initialiseLatLngFromAddress(end);
        endLatLng = latLong.getLatLng();
        checkValid.calculateDistanceMatrix(startLatLng, endLatLng, TravelMode.DRIVING);
        if (checkValid.getTravelTime().equals("null")) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) // short circuit if same object
                || (other instanceof MapAddress // instanceof handles nulls
                && this.value.equals(((MapAddress) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
