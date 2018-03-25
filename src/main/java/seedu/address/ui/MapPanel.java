package seedu.address.ui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEvent;

/**
 * The panel for google maps. Construct the maps view which is return by calling
 * getMapView() to MainWindow which attaches it to mapPanelPlaceHolder. After which it initialises the Map contents
 * mapInitialised()
 */
public class MapPanel extends UiPart<Region> implements MapComponentInitializedListener {

    public static final double LATITUDE_SG = 1.3607962;
    public static final double LONGITUDE_SG = 103.8109208;
    public static final int DEFAULT_ZOOM_LEVEL = 10;
    private static final String FXML = "MapsPanel.fxml";
    private static GoogleMap actualMap;

    private GoogleMapView mapView;
    public MapPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        mapView = new GoogleMapView();
        mapView.setDisableDoubleClick(true);
        mapView.addMapInializedListener(this);
        mapView.getWebview().getEngine().setOnAlert((WebEvent<String> event) -> {
        });
    }

    @Override
    public void mapInitialized() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> mapView.getMap().hideDirectionsPane());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        t.start();
        actualMap = setMapOptions();
    }

    /**
     * Set the Map options for initialisation of {@code actualMap}
     */
    private GoogleMap setMapOptions() {
        LatLong center = new LatLong(LATITUDE_SG, LONGITUDE_SG);
        MapOptions options = new MapOptions();
        options.center(center)
                .zoom(DEFAULT_ZOOM_LEVEL)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.ROADMAP);
        return mapView.createMap(options);
    }

    public GoogleMapView getMapView() {
        return mapView;
    }

    public static GoogleMap getMap() {
        return actualMap;
    }
}
