import java.util.Date;

public class Ticket {
    private int bookingId;
    private String passengerName;
    private int seatNumber;
    private Date bookingDate;

    public Ticket(int bookingId, String passengerName, int seatNumber) {
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;

        this.bookingDate = new Date();
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Date getBookingDate() {
        return bookingDate;
    }
}
