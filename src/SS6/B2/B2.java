package SS6.B2;
public class B2 {

    public static void main(String[] args) throws Exception {

        TicketPool roomA = new TicketPool("A", 3);
        TicketPool roomB = new TicketPool("B", 5);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB);

        counter1.start();
        counter2.start();

        // Sau 5 giây nhà cung cấp thêm vé
        Thread.sleep(5000);

        roomA.addTickets(3);
    }
}


// ============================
// TicketPool
// ============================

class TicketPool {

    private String room;
    private int tickets;
    private int ticketNumber = 1;

    public TicketPool(String room, int tickets) {
        this.room = room;
        this.tickets = tickets;
    }

    public synchronized String sellTicket(String counterName) {

        try {

            while (tickets == 0) {
                System.out.println(counterName + ": Hết vé phòng "
                        + room + ", đang chờ...");
                wait(); // chờ vé mới
            }

            String ticket =
                    room + "-" + String.format("%03d", ticketNumber++);

            tickets--;

            System.out.println(counterName + " bán vé " + ticket);

            return ticket;

        } catch (InterruptedException e) {
            return null;
        }
    }

    public synchronized void addTickets(int amount) {

        tickets += amount;

        System.out.println("Nhà cung cấp: Đã thêm "
                + amount + " vé vào phòng " + room);

        notifyAll(); // đánh thức các quầy
    }
}


// ============================
// BookingCounter
// ============================

class BookingCounter extends Thread {

    private String name;
    private TicketPool pool;

    public BookingCounter(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {

        while (true) {

            pool.sellTicket(name);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }
    }
}