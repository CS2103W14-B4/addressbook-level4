package seedu.address.model.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsWaypoint;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import com.lynden.gmapsfx.shapes.Polygon;
import com.lynden.gmapsfx.shapes.PolygonOptions;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEvent;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/**
 * Example Application for creating and loading a GoogleMap into a JavaFX
 * application
 */
public class Map extends Application implements MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback{

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    protected DirectionsPane directions;

    private MarkerOptions markerOptions2;
    private Marker myMarker2;
    private Button btnHideMarker;
    private Button btnDeleteMarker;


    @Override
    public void start(final Stage stage) {
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);
        mapComponent.setDisableDoubleClick(true);
        mapComponent.getWebview().getEngine().setOnAlert((WebEvent<String> event) -> {
        });

        BorderPane bp = new BorderPane();

        bp.setCenter(mapComponent);

        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();

    }

    DirectionsRenderer renderer;

    @Override
    public void mapInitialized() {
        Thread t = new Thread( () -> {
            try {
                Thread.sleep(3000);
                System.out.println("Calling showDirections from Java");
                Platform.runLater(() -> mapComponent.getMap().hideDirectionsPane());
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        });
        t.start();
        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(47.606189, -122.335842);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });

        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);//map type

        map = mapComponent.createMap(options);
/*        directions = mapComponent.getDirec(); //required for getting direction

        MarkerOptions markOptions = new MarkerOptions();
        markOptions.animation(Animation.DROP)
                .position(new LatLong(1.3983,103.909))
                .title("home")
                .visible(true);
        Marker location = new Marker(markOptions);

        //Shows location in map
        map.setHeading(123.2);
        map.setCenter(new LatLong(1.3983,103.909));
        map.addMarker(location);
        map.setZoom(15);
//        System.out.println("Heading is: " + map.getHeading() );

        //Direction from start to end
        DirectionsService ds = new DirectionsService();
        renderer = new DirectionsRenderer(true, map, directions);

        //    DirectionsWaypoint[] dw = new DirectionsWaypoint[1];
        //   dw[0] = new DirectionsWaypoint("Punggol");
        //   dw[1] = new DirectionsWaypoint("Sengkang");

        DirectionsRequest dr = new DirectionsRequest(
                "NUS",
                "Punggol",
                TravelModes.DRIVING);
        ds.getRoute(dr, this, renderer);
        //map.showDirectionPane();*/


    }


    private void hideMarker() {
//		System.out.println("deleteMarker");

        boolean visible = myMarker2.getVisible();

        //System.out.println("Marker was visible? " + visible);

        myMarker2.setVisible(! visible);

//				markerOptions2.visible(Boolean.FALSE);
//				myMarker2.setOptions(markerOptions2);
//		System.out.println("deleteMarker - made invisible?");
    }

    private void deleteMarker() {
        //System.out.println("Marker was removed?");
        map.removeMarker(myMarker2);
    }

    private void checkCenter(LatLong center) {
//        System.out.println("Testing fromLatLngToPoint using: " + center);
//        Point2D p = map.fromLatLngToPoint(center);
//        System.out.println("Testing fromLatLngToPoint result: " + p);
//        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

    @Override
    public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
        if(status.equals(ElevationStatus.OK)){
            for(ElevationResult e : results){
                System.out.println(" Elevation on "+ e.getLocation().toString() + " is " + e.getElevation());
            }
        }
    }

    @Override
    public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
        if(status.equals(GeocoderStatus.OK)){
            for(GeocodingResult e : results){
                System.out.println(e.getVariableName());
                System.out.println("GEOCODE: " + e.getFormattedAddress() + "\n" + e.toString());
            }

        }

    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        if(status.equals(DirectionStatus.OK)){

            System.out.println("OK");

            DirectionsResult e = results;
            GeocodingService gs = new GeocodingService();

            System.out.println("SIZE ROUTES: " + e.getRoutes().size() + "\n" + "ORIGIN: " + e.getRoutes().get(0).getLegs().get(0).getStartLocation());
            //gs.reverseGeocode(e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLatitude(), e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLongitude(), this);
            System.out.println("LEGS SIZE: " + e.getRoutes().get(0).getLegs().size());
            System.out.println("WAYPOINTS " +e.getGeocodedWaypoints().size());
            /*double d = 0;
            for(DirectionsLeg g : e.getRoutes().get(0).getLegs()){
                d += g.getDistance().getValue();
                System.out.println("DISTANCE " + g.getDistance().getValue());
            }*/
            try{
                System.out.println("Distancia total = " + e.getRoutes().get(0).getLegs().get(0).getDistance().getText());
            } catch(Exception ex){
                System.out.println("ERRO: " + ex.getMessage());
            }
            System.out.println("LEG(0)");
            System.out.println(e.getRoutes().get(0).getLegs().get(0).getSteps().size());
            /*for(DirectionsSteps ds : e.getRoutes().get(0).getLegs().get(0).getSteps()){
                System.out.println(ds.getStartLocation().toString() + " x " + ds.getEndLocation().toString());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(ds.getStartLocation())
                        .title(ds.getInstructions())
                        .animation(Animation.DROP)
                        .visible(true);
                Marker myMarker = new Marker(markerOptions);
                map.addMarker(myMarker);
            }
                    */
            System.out.println(renderer.toString());
        }
    }
}

