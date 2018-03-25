package seedu.address.logic.map;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Converts address to LatLng form.
 */
public class Geocoding {

    /**
     * API Key required for requesting service from google server
     */
    public static final String API_KEY = "AIzaSyAD8_oIBJlzOp30VA9mOvQKp6GZe8SFsYY";
    private static LatLng location;
    private GeoApiContext context;

    /**
     * Initialises access to google server
     */
    public Geocoding() {
        context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
    }

    /**
     * Send request to google server to obtain {@code results}
     * Where that {@code location} is extracted in LatLng form.
     * @param address
     */
    public void initialiseLatLngFromAddress(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    address).await();
            getLocation(results);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLocation(GeocodingResult[] results) {
        try {
            location = results[0].geometry.location;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }

    public double getLat() {
        return location.lat;
    }

    public double getLong() {
        return location.lng;
    }

    public LatLng getLatLng() {
        return location;
    }

    /**
     * Checks if the {@code address} can be found in google server
     * @param address
     * @return boolean
     */
    public boolean checkIfAddressCanBeFound(String address) {

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    address).await();
            getLocation(results);
        } catch (ApiException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}
