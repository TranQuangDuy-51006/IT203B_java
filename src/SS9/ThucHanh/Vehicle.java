package session9.ThucHanh;

public abstract class Vehicle implements Runnable{
    protected String name;        // tên xe
    protected boolean canGo = false; // có được đi chưa (do đèn quyết định)
    protected Intersection intersection; // giao lộ

    public Vehicle(String name, Intersection intersection) {
        this.name = name;
        this.intersection = intersection;
    }

    // phân biệt xe ưu tiên hay không
    public abstract boolean isPriority();

    // nhận tín hiệu từ đèn giao thông (Observer)
    public void update(String light) {
        // nếu đèn xanh thì được đi
        canGo = light.equals("GREEN");
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " đến ngã tư");

            // nếu là xe thường → phải chờ đèn xanh
            // nếu là xe ưu tiên → bỏ qua
            while (!canGo && !isPriority()) {
                Thread.sleep(200); // chờ 1 chút
            }

            // vào giao lộ (critical section)
            intersection.enter(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
