package SS8.B4;

import java.util.*;

// Observer
interface Observer {
    void update(int temperature);
}

// Subject
interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}

// Sensor - phát tín hiệu
class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    public void attach(Observer o) {
        observers.add(o);
    }

    public void detach(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int t) {
        temperature = t;
        System.out.println("Cảm biến: Nhiệt độ = " + temperature);
        notifyObservers(); // tự động thông báo
    }
}

// Quạt
class Fan implements Observer {
    public void update(int t) {
        if (t < 20)
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        else if (t <= 25)
            System.out.println("Quạt: Nhiệt độ trung bình, chạy mức vừa");
        else
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
    }
}

// Máy tạo ẩm
class Humidifier implements Observer {
    public void update(int t) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + t);
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TemperatureSensor sensor = new TemperatureSensor();

        while (true) {
            System.out.println("\n1. Đăng ký quạt");
            System.out.println("2. Đăng ký máy tạo ẩm");
            System.out.println("3. Set nhiệt độ");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sensor.attach(new Fan());
                    System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    break;

                case 2:
                    sensor.attach(new Humidifier());
                    System.out.println("Máy tạo ẩm: Đã đăng ký");
                    break;

                case 3:
                    System.out.println("Nhập nhiệt độ:");
                    int t = sc.nextInt();
                    sensor.setTemperature(t);
                    break;

                case 0:
                    return;
            }
        }
    }
}
