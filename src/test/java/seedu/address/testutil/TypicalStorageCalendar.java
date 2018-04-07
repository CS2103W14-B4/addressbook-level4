package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.calendar.StorageCalendar;

/**
 * A utility class containing a list of {@code Appointments} objects to be used in tests.
 */
public class TypicalStorageCalendar {

    public static final StorageCalendar EMPTY_CALENDAR = new StorageCalendar("Storage Calendar");

    //@@author tzerbin
    public static final Appointment CONCERT = new Appointment(
            new AppointmentBuilder().withName("Concert")
                    .withStartTime("19:00")
                    .withStartDate("24-08-2018")
                    .withLocation("Singapore Indoors Stadium")
                    .withEndTime("23:00")
                    .withEndDate("24-08-2018").build());

    public static final Appointment DENTAL = new Appointment(
            new AppointmentBuilder().withName("Dental")
                    .withStartTime("15:30")
                    .withStartDate("25-08-2018")
                    .withLocation("Singapore General Hospital")
                    .withEndTime("15:50")
                    .withEndDate("25-08-2018").build());

    public static final Appointment MEETING = new Appointment(
            new AppointmentBuilder().withName("Meeting")
                    .withStartTime("10:30")
                    .withStartDate("26-08-2018")
                    .withLocation("Mediacorp Campus")
                    .withEndTime("18:00")
                    .withEndDate("26-08-2018").build());

    public static List<Appointment> getTypicalAppointmentList() {
        return new ArrayList<>(Arrays.asList(CONCERT, DENTAL, MEETING));
    }
}