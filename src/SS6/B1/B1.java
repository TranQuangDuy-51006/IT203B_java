package SS6.B1;

public class B1 {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== DEMO DEADLOCK =====");

        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 2);

        BookingCounter counter1 =
                new BookingCounter("Quầy 1", roomA, roomB, false);

        BookingCounter counter2 =
                new BookingCounter("Quầy 2", roomA, roomB, true);

        counter1.start();
        counter2.start();

        Thread.sleep(4000);

        System.out.println("\n===== FIX DEADLOCK =====");

        roomA = new TicketPool("A", 2);
        roomB = new TicketPool("B", 2);

        BookingCounter counter3 =
                new BookingCounter("Quầy 1", roomA, roomB, false);

        BookingCounter counter4 =
                new BookingCounter("Quầy 2", roomA, roomB, false);

        counter3.start();
        counter4.start();
    }
}

// ============================
// TicketPool
// ============================

class TicketPool {

    private String room;
    private int tickets;

    public TicketPool(String room, int tickets) {
        this.room = room;
        this.tickets = tickets;
    }

    public String getRoom() {
        return room;
    }

    public String takeTicket() {
        if (tickets > 0) {
            String ticket = room + "-" + String.format("%03d", tickets);
            tickets--;
            return ticket;
        }
        return null;
    }

    public void returnTicket() {
        tickets++;
    }
}

// ============================
// BookingCounter
// ============================

class BookingCounter extends Thread {

    private String name;
    private TicketPool roomA;
    private TicketPool roomB;
    private boolean reverse;

    public BookingCounter(String name, TicketPool roomA,
                          TicketPool roomB, boolean reverse) {

        this.name = name;
        this.roomA = roomA;
        this.roomB = roomB;
        this.reverse = reverse;
    }

    public void sellCombo() {

        TicketPool first = reverse ? roomB : roomA;
        TicketPool second = reverse ? roomA : roomB;

        synchronized (first) {

            String ticket1 = first.takeTicket();
            System.out.println(name + " đã lấy vé " + ticket1);

            try { Thread.sleep(1000); } catch (Exception e) {}

            System.out.println(name + " đang chờ vé phòng " + second.getRoom());

            synchronized (second) {

                String ticket2 = second.takeTicket();

                if (ticket1 != null && ticket2 != null) {

                    System.out.println(name +
                            " bán combo thành công: "
                            + ticket1 + " & " + ticket2);

                } else {

                    if (ticket1 != null) first.returnTicket();
                    if (ticket2 != null) second.returnTicket();

                    System.out.println(name +
                            " bán combo thất bại");
                }
            }
        }
    }

    @Override
    public void run() {
        sellCombo();
    }
}