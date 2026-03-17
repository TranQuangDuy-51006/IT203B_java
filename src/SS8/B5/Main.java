package SS8.B5;

import java.util.*;

// ===== COMMAND =====
interface Command {
    void execute();
}

// Macro command - chế độ ngủ
class SleepModeCommand implements Command {
    private Light light;
    private Fan fan;
    private AirConditioner ac;

    public SleepModeCommand(Light l, Fan f, AirConditioner ac) {
        this.light = l;
        this.fan = f;
        this.ac = ac;
    }

    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        System.out.println("SleepMode: Điều hòa set 28°C");
        System.out.println("SleepMode: Quạt tốc độ thấp");

        light.off();
        ac.setTemp(28);
        fan.low();
    }
}

// ===== OBSERVER =====
interface Observer {
    void update(int temp);
}

interface Subject {
    void attach(Observer o);
    void notifyObservers();
}

// Sensor
class TemperatureSensor implements Subject {
    private List<Observer> list = new ArrayList<>();
    private int temp;

    public void attach(Observer o) {
        list.add(o);
    }

    public void notifyObservers() {
        for (Observer o : list) {
            o.update(temp);
        }
    }

    public void setTemperature(int t) {
        temp = t;
        System.out.println("Cảm biến: Nhiệt độ = " + temp);
        notifyObservers();
    }
}

// ===== DEVICE =====

// Đèn
class Light {
    private boolean isOn = true;

    void off() {
        isOn = false;
        System.out.println("Đèn: Tắt");
    }

    public String status() {
        return isOn ? "Bật" : "Tắt";
    }
}

// Quạt (observer)
class Fan implements Observer {
    private String speed = "Tắt";

    void low() {
        speed = "Thấp";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void update(int t) {
        if (t > 30) {
            speed = "Mạnh";
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }

    public String status() {
        return speed;
    }
}

// Điều hòa (observer)
class AirConditioner implements Observer {
    private int temp = 25;

    void setTemp(int t) {
        temp = t;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    public void update(int t) {
        if (t > 30) {
            System.out.println("Điều hòa: Giữ nhiệt độ = " + temp);
        }
    }

    public String status() {
        return "Temp=" + temp;
    }
}

// ===== MAIN =====
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        TemperatureSensor sensor = new TemperatureSensor();

        // đăng ký observer
        sensor.attach(fan);
        sensor.attach(ac);

        // command
        Command sleepCmd = new SleepModeCommand(light, fan, ac);

        while (true) {
            System.out.println("\n1. Chế độ ngủ");
            System.out.println("2. Set nhiệt độ");
            System.out.println("3. Xem trạng thái");
            System.out.println("0. Thoát");

            int c = sc.nextInt();

            switch (c) {
                case 1:
                    sleepCmd.execute();
                    break;

                case 2:
                    System.out.println("Nhập nhiệt độ:");
                    int t = sc.nextInt();
                    sensor.setTemperature(t);
                    break;

                case 3:
                    System.out.println("Đèn: " + light.status());
                    System.out.println("Quạt: " + fan.status());
                    System.out.println("Điều hòa: " + ac.status());
                    break;

                case 0:
                    return;
            }
        }
    }
}