package SS6.B3;

public class B3 {

    public static void main(String[] args) throws Exception {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB);

        TicketSupplier supplier =
                new TicketSupplier(roomA, roomB, 3, 3000, 3);

        Thread supplierThread = new Thread(supplier);

        counter1.start();
        counter2.start();
        supplierThread.start();

        counter1.join();
        counter2.join();
        supplierThread.join();

        System.out.println("\n===== KẾT QUẢ =====");
        System.out.println("Quầy 1 bán được: " + counter1.getSoldCount());
        System.out.println("Quầy 2 bán được: " + counter2.getSoldCount());
        System.out.println("Vé còn lại phòng A: " + roomA.getRemaining());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemaining());
    }
}


// ======================
// TicketPool
// ======================

class TicketPool {

    private String room;
    private int tickets;
    private int ticketNumber = 1;

    public TicketPool(String room, int tickets) {
        this.room = room;
        this.tickets = tickets;
    }

    public synchronized String sellTicket() {

        if (tickets <= 0) {
            return null;
        }

        String ticket =
                room + "-" + String.format("%03d", ticketNumber++);

        tickets--;

        return ticket;
    }

    public synchronized void addTickets(int count) {

        tickets += count;

        System.out.println("Nhà cung cấp: Đã thêm "
                + count + " vé vào phòng " + room);
    }

    public synchronized int getRemaining() {
        return tickets;
    }
}


// ======================
// BookingCounter
// ======================

class BookingCounter extends Thread {

    private String name;
    private TicketPool pool;
    private int soldCount = 0;

    public BookingCounter(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    public int getSoldCount() {
        return soldCount;
    }

    @Override
    public void run() {

        while (true) {

            String ticket = pool.sellTicket();

            if (ticket == null) {
                break;
            }

            soldCount++;

            System.out.println(name + " đã bán vé " + ticket);

            try {
                Thread.sleep(500);
            } catch (Exception e) {}
        }
    }
}


// ======================
// TicketSupplier
// ======================

class TicketSupplier implements Runnable {

    private TicketPool roomA;
    private TicketPool roomB;
    private int supplyCount;
    private int interval;
    private int rounds;

    public TicketSupplier(TicketPool roomA, TicketPool roomB,
                          int supplyCount, int interval, int rounds) {

        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.rounds = rounds;
    }

    @Override
    public void run() {

        for (int i = 0; i < rounds; i++) {

            try {
                Thread.sleep(interval);
            } catch (Exception e) {}

            roomA.addTickets(supplyCount);
            roomB.addTickets(supplyCount);
        }
    }
}