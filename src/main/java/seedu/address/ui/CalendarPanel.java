package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_CALENDARVIEW;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeCalendarViewPageRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * The panel for the Calendar. Constructs a calendar view and attaches to it a CalendarSource.
 * The view is then returned by calling getCalendarView in MainWindow to attach it to the
 * calendarPlaceholder.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private CalendarView calendarView;

    @FXML
    private WebView browser;

    public CalendarPanel(CalendarSource calendarSource) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().clear(); // there is an existing default source when creating the view
        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        this.calendarView = calendarView;

        registerAsAnEventHandler(this);
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    @Subscribe
    private void handleChangeCalendarViewPageRequestEvent(ChangeCalendarViewPageRequestEvent event) {
        Platform.runLater(() -> {
            switch (event.calendarViewPage) {

            case "day":
                calendarView.showDayPage();
                break;
            case "week":
                calendarView.showWeekPage();
                break;
            case "month":
                calendarView.showMonthPage();
                break;
            case "year":
                calendarView.showYearPage();
                break;

            default:
                try {
                    throw new ParseException(MESSAGE_UNKNOWN_CALENDARVIEW);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //methods from BrowserPanel
    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
