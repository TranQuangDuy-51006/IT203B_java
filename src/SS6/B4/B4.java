package SS6.B4;

import java.util.*;

public class B4 {

    public static void main(String[] args) throws Exception {

        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter counter1 =
                new BookingCounter("Quầy 1", roomA, roomB);

        BookingCounter counter2 =
                new BookingCounter("Quầy 2", roomA, roomB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\n===== KẾT QUẢ =====");
        System.out.println("Quầy 1 bán được: " + counter1.getSoldCount());
        System.out.println("Quầy 2 bán được: " + counter2.getSoldCount());
        System.out.println("Vé còn lại phòng A: " + roomA.getRemaining());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemaining());
    }
}


// ============================
// Ticket
// ============================

class Ticket {

    String ticketId;
    String roomName;
    boolean isSold;

    public Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }
}


// ============================
// TicketPool
// ============================

class TicketPool {

    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int count) {

        this.roomName = roomName;

        for (int i = 1; i <= count; i++) {

            String id = roomName + "-" + String.format("%03d", i);

            tickets.add(new Ticket(id, roomName));
        }
    }

    public synchronized Ticket sellTicket() {

        for (Ticket t : tickets) {

            if (!t.isSold) {

                t.isSold = true;

                return t;
            }
        }

        return null;
    }

    public synchronized int getRemaining() {

        int count = 0;

        for (Ticket t : tickets) {

            if (!t.isSold) count++;
        }

        return count;
    }
}


// ============================
// BookingCounter
// ============================

class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private int soldCount = 0;

    private Random random = new Random();

    public BookingCounter(String counterName,
                          TicketPool roomA,
                          TicketPool roomB) {

        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    public int getSoldCount() {
        return soldCount;
    }

    @Override
    public void run() {

        while (true) {

            Ticket ticket;

            if (random.nextBoolean()) {
                ticket = roomA.sellTicket();
            } else {
                ticket = roomB.sellTicket();
            }

            if (ticket == null) {

                // thử phòng còn lại
                ticket = roomA.sellTicket();
                if (ticket == null) {
                    ticket = roomB.sellTicket();
                }
            }

            if (ticket == null) {
                break; // hết vé cả 2 phòng
            }

            soldCount++;

            System.out.println(counterName
                    + " đã bán vé "
                    + ticket.ticketId);

            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }
    }
}