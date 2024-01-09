import java.sql.Connection;

public interface TicketBookingOperations {
    void viewBookedTickets(Connection con);
    void changeBookedTickets(Connection b);
}