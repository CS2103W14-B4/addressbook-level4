package seedu.address.model.person;

import seedu.address.model.calendar.CelebCalendar;

/**
 *  Child class of Person for those who are tagged celebrities
 */
public class Celebrity extends Person {

    private CelebCalendar celebCalendar;

    /**
     * Every field must be present and not null.
     */
    public Celebrity(Person celeb) {
        super(celeb.getName(), celeb.getPhone(), celeb.getEmail(), celeb.getAddress(), celeb.getTags());
        this.celebCalendar = new CelebCalendar(this.getName().fullName);
    }

    public CelebCalendar getCelebCalendar() {
        return celebCalendar;
    }

    /**
     * Sets the celebCalendar to another one.
     * @param newCelebCalendar
     */
    public void setCelebCalendar(CelebCalendar newCelebCalendar) {
        this.celebCalendar = newCelebCalendar;
    }

    /**
     * @param celeb
     * @return true if input celeb is a copy of this celebrity
     */
    public boolean isCopyOf(Celebrity celeb) {
        return super.equals(celeb);
    }
}