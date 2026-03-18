package session9.ThucHanh;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Intersection {
    // hàng đợi xe (thread-safe)
    private BlockingQueue<Vehicle> queue = new LinkedBlockingQueue<>(5);

    // synchronized → chỉ 1 thread vào tại 1 thời điểm
    public synchronized void enter(Vehicle v) throws Exception {

        // nếu quá 5 xe → kẹt xe
        if (queue.size() >= 5) {
            throw new TrafficJamException(" Kẹt xe!");
        }

        // thêm xe vào hàng đợi
        queue.add(v);

        System.out.println(v.name + " đang qua ngã tư");

        Thread.sleep(500); // giả lập thời gian qua

        // xe rời đi
        queue.remove(v);

        System.out.println(v.name + " đã qua");

        // thống kê
        stats();
    }

    // dùng Stream API để thống kê
    public void stats() {
        long priority = queue.stream().filter(Vehicle::isPriority).count();
        long normal = queue.size() - priority;

        System.out.println(" Thường: " + normal + " |  Ưu tiên: " + priority);
    }
}
