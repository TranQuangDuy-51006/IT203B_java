package session9.ThucHanh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void main(String[] args) {
        // tạo giao lộ
        Intersection intersection = new Intersection();

        // tạo đèn giao thông
        TrafficLight light = new TrafficLight();
        light.start(); // chạy thread đèn

        // thread pool (quản lý nhiều xe)
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // tạo 10 xe
        for (int i = 0; i < 10; i++) {
            Vehicle v = VehicleFactory.create(intersection);

            light.add(v);     // đăng ký observer
            pool.execute(v);  // chạy thread
        }

        pool.shutdown();
    }
}
