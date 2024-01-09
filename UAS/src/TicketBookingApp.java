import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TicketBookingApp {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/booked_tickets", "root", "")) {
            initializeDatabase(connection);

            TicketBookingSystem bookingSystem = new TicketBookingSystem(connection, 30);
            Scanner scanner = new Scanner(System.in);

            try {
                while (true) {
                    System.out.println("");
                    System.out.println("========================");
                    System.out.println("--    Booking Pedia   --");
                    System.out.println("========================");
                    System.out.println("");
                    System.out.println("           Menu         ");
                    System.out.println("");
                    System.out.println("1. Book a Ticket ");
                    System.out.println("2. View Booked Tickets");
                    System.out.println("3. Change Booked Tickets");
                    System.out.println("4. Cancel Booking");
                    System.out.println("5. Exit");
                    System.out.println("");
                    System.out.println("========================");
                    System.out.println("");
                    System.out.print("Choose an option: ");
            
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
            
                    switch (choice) {
                        case 1:
                            System.out.print("Enter passenger name: ");
                            String passengerName = scanner.nextLine();
                            System.out.print("Enter seat number: ");
                            int seatNumber = scanner.nextInt();
                            bookingSystem.bookTicket(passengerName, seatNumber);
                            break;
                        case 2:
                            bookingSystem.viewBookedTickets(connection);
                            break;
                        case 3:
                            // Panggil fungsi changeBookedTickets dari bookingSystem
                            bookingSystem.changeBookedTickets(connection);
                            break;
                        case 4:
                            System.out.print("Enter booking ID to cancel: ");
                            int cancelBookingId = scanner.nextInt();
                            CancelBooking cancel = new CancelBooking();
                            cancel.cancelBooking(cancelBookingId, connection);
                            break;
                        case 5:
                            System.out.println("Exiting the Ticket Booking System. Thank you!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

            } finally {
                // Close resources, such as database connections
                scanner.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inisialisasi tabel booked_tickets jika belum ada
    private static void initializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS booked_tickets (" +
                "booking_id INT PRIMARY KEY," +
                "passenger_name VARCHAR(255)," +
                "seat_number INT," +
                "booking_date TIMESTAMP)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }
}
