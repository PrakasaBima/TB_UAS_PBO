import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CancelBooking extends TicketBookingSystem {

    public void cancelBooking(int bookingId, Connection con) {
        try {
            // Hapus data pemesanan tiket dari database
            String deleteQuery = "DELETE FROM booked_tickets WHERE booking_id = ?";
            // bookedTickets.removeIf(ticket -> ticket.getBookingId() == bookingId);
            try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, bookingId);
                preparedStatement.executeUpdate();
            }

            System.out.println("Booking canceled successfully. Booking ID: " + bookingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
