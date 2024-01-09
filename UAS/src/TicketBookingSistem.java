import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class TicketBookingSystem implements TicketBookingOperations {
    protected List<Ticket> bookedTickets;
    private int bookingId;
    private Connection connection;
    private int totalSeats;
    

    public TicketBookingSystem(){};
    public TicketBookingSystem(String a){};
    public TicketBookingSystem(int b){};
    public TicketBookingSystem(int b, Connection c){};

    public TicketBookingSystem(Connection connection, int totalSeats) {
        this.connection = connection;
        bookedTickets = new ArrayList<>();
        this.totalSeats = totalSeats;
    }



    public void bookTicket(String passengerName, int seatNumber) {
        int bookingId = bookedTickets.size() + 1;
        Ticket ticket = new Ticket(bookingId, passengerName, seatNumber);
        bookedTickets.add(ticket);

        try {
            // Simpan data pemesanan tiket ke dalam database
            String insertQuery = "INSERT INTO booked_tickets (booking_id, passenger_name, seat_number, booking_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, bookingId);
                preparedStatement.setString(2, passengerName);
                preparedStatement.setInt(3, seatNumber);
                preparedStatement.setTimestamp(4, new Timestamp(ticket.getBookingDate().getTime()));
                preparedStatement.executeUpdate();
            }

            System.out.println("\nTicket booked successfully. Booking ID: \n" + bookingId);
        } catch (SQLException e) {
            System.out.println("Seat Is Already Booked!!!");
        }
    }

    @Override
        public void changeBookedTickets(Connection con) {
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter Booking ID to update: ");
                int bookingIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.print("Enter new passenger name: ");
                String newPassengerName = scanner.nextLine();

                System.out.print("Enter new seat number: ");
                int newSeatNumber = scanner.nextInt();

                // Query untuk melakukan update pada database
                String updateQuery = "UPDATE booked_tickets SET passenger_name = ?, seat_number = ? WHERE booking_id = ?";
                
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, newPassengerName);
                    preparedStatement.setInt(2, newSeatNumber);
                    preparedStatement.setInt(3, bookingIdToUpdate);
                    
                    // Menjalankan pernyataan update
                    int rowsAffected = preparedStatement.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("Update successful for booking ID " + bookingIdToUpdate);
                    } else {
                        System.out.println("No booking found for ID " + bookingIdToUpdate);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    @Override
    public void viewBookedTickets(Connection con) {

        System.out.println("Booked Tickets:");
        try {

            Statement statement = con.createStatement();

            String query = "SELECT * FROM booked_tickets";
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData met = resultSet.getMetaData();
            int count = met.getColumnCount();

            for (int i = 1; i <= count; i++) {
                System.out.print(met.getColumnName(i)+"||");
            }
            System.out.println();

            while (resultSet.next()) {
                for (int j = 1; j <= count; j++) {
                    System.out.print(resultSet.getString(j)+"\t||");
                }
                System.out.println();
            }

            System.out.println();

            resultSet.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // for (Ticket ticket : bookedTickets) {
        //     System.out.println("Booking ID: " + ticket.getBookingId());
        //     System.out.println("Passenger Name: " + ticket.getPassengerName());
        //     System.out.println("Seat Number: " + ticket.getSeatNumber());
        //     System.out.println("Booking Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ticket.getBookingDate()));
        //     System.out.println();
        }
    
        // DELETE ------------------------------

    
}

    // @Override
    // public void displayAvailableSeats() {
    // // Your implementation here
    // System.out.println("Available Seats:");
    // for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
    //     boolean isBooked = false;
    //     for (Ticket ticket : bookedTickets) {
    //         if (ticket.getSeatNumber() == seatNumber) {
    //             isBooked = true;
    //             break;
    //         }
    //     }
    //     if (!isBooked) {
    //         System.out.println("Seat Number: " + seatNumber);
    //     }
    // }
    // }
    // @Override
    // public void displayBookedSeats() {
    //     System.out.println("Booked Seats:");
    //     for (Ticket ticket : bookedTickets) {
    //         System.out.println("Seat Number: " + ticket.getSeatNumber());
    //     }
    // }
