package session9.ThucHanh;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TrafficLight extends Thread{
    private TrafficLightState state = new Red(); // trạng thái ban đầu
    private List<Vehicle> observers = new CopyOnWriteArrayList<>();

    public TrafficLight() {
        setDaemon(true); // chạy nền
    }

    // thêm xe vào danh sách theo dõi
    public void add(Vehicle v) {
        observers.add(v);
    }

    // gửi tín hiệu cho tất cả xe
    private void notifyAllVehicles() {
        for (Vehicle v : observers) {
            v.update(state.color());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000); // mỗi 3s đổi đèn

                state = state.next(); // chuyển state

                System.out.println(" Đèn: " + state.color());

                notifyAllVehicles(); // báo cho xe
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
