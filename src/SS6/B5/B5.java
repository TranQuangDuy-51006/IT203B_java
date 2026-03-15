package SS6.B5;

import java.util.*;

public class B5 {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A",5);
        TicketPool roomB = new TicketPool("B",4);
        TicketPool roomC = new TicketPool("C",3);

        List<TicketPool> pools = Arrays.asList(roomA,roomB,roomC);

        Thread manager = new Thread(new TimeoutManager(pools));
        manager.start();

        for(int i=1;i<=5;i++){
            Thread t = new Thread(
                    new BookingCounter("Quầy "+i,pools));
            t.start();
        }
    }
}


// ======================
// Ticket
// ======================

class Ticket{

    String ticketId;
    String roomName;

    boolean isSold=false;
    boolean isHeld=false;
    boolean isVIP=false;

    long holdExpiryTime=0;

    public Ticket(String id,String room){
        ticketId=id;
        roomName=room;
    }
}


// ======================
// TicketPool
// ======================

class TicketPool{

    private String roomName;
    private List<Ticket> tickets=new ArrayList<>();

    public TicketPool(String roomName,int count){

        this.roomName=roomName;

        for(int i=1;i<=count;i++){
            String id=roomName+"-"+String.format("%03d",i);
            tickets.add(new Ticket(id,roomName));
        }
    }

    public synchronized Ticket holdTicket(boolean vip,String counter){

        for(Ticket t:tickets){

            if(!t.isSold && !t.isHeld){

                t.isHeld=true;
                t.isVIP=vip;
                t.holdExpiryTime=System.currentTimeMillis()+5000;

                System.out.println(counter+
                        ": Đã giữ vé "+t.ticketId+
                        (vip?" (VIP)":"")+
                        ". Thanh toán trong 5s");

                return t;
            }
        }

        return null;
    }

    public synchronized void sellHeldTicket(Ticket t,String counter){

        if(t==null) return;

        if(t.isHeld){

            t.isHeld=false;
            t.isSold=true;

            System.out.println(counter+
                    ": Thanh toán thành công "+
                    t.ticketId);
        }
    }

    public synchronized void releaseExpiredTickets(){

        long now=System.currentTimeMillis();

        for(Ticket t:tickets){

            if(t.isHeld && !t.isSold &&
                    t.holdExpiryTime<now){

                t.isHeld=false;

                System.out.println(
                        "TimeoutManager: Vé "
                                +t.ticketId+
                                " hết hạn giữ, đã trả lại kho");
            }
        }
    }
}


// ======================
// BookingCounter
// ======================

class BookingCounter implements Runnable{

    private String name;
    private List<TicketPool> pools;
    private Random rand=new Random();

    public BookingCounter(String name,List<TicketPool> pools){
        this.name=name;
        this.pools=pools;
    }

    public void run(){

        while(true){

            TicketPool pool=
                    pools.get(rand.nextInt(pools.size()));

            boolean vip=rand.nextInt(5)==0;

            Ticket t=pool.holdTicket(vip,name);

            if(t!=null){

                try{
                    Thread.sleep(3000);
                }catch(Exception e){}

                pool.sellHeldTicket(t,name);
            }

            try{
                Thread.sleep(1000);
            }catch(Exception e){}
        }
    }
}


// ======================
// TimeoutManager
// ======================

class TimeoutManager implements Runnable{

    private List<TicketPool> pools;

    public TimeoutManager(List<TicketPool> pools){
        this.pools=pools;
    }

    public void run(){

        while(true){

            for(TicketPool p:pools){
                p.releaseExpiredTickets();
            }

            try{
                Thread.sleep(1000);
            }catch(Exception e){}
        }
    }
}