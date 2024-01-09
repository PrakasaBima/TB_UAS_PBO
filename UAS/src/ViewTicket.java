import java.text.SimpleDateFormat;

public class ViewTicket extends TicketBookingSystem {

    public ViewTicket( String bookedTicked){
        super(bookedTicked);

        System.out.println("Booked Tickets:");
        for (Ticket ticket : bookedTickets) {
            System.out.println("Booking ID: " + ticket.getBookingId());
            System.out.println("Passenger Name: " + ticket.getPassengerName());
            System.out.println("Seat Number: " + ticket.getSeatNumber());
            System.out.println("Booking Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ticket.getBookingDate()));
            System.out.println();
        }
        }
    }
