package SS8.B1;

import java.util.*;

// Singleton - chỉ 1 kết nối
class HardwareConnection {
    private static HardwareConnection instance;

    private HardwareConnection() {}

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
            System.out.println("HardwareConnection: Đã kết nối phần cứng.");
        }
        return instance;
    }

    public void disconnect() {
        System.out.println("HardwareConnection: Ngắt kết nối.");
    }
}

// Interface thiết bị
interface Device {
    void turnOn();
    void turnOff();
}

// Đèn
class Light implements Device {
    public void turnOn() {
        System.out.println("Đèn: Bật sáng.");
    }

    public void turnOff() {
        System.out.println("Đèn: Tắt.");
    }
}

// Quạt
class Fan implements Device {
    public void turnOn() {
        System.out.println("Quạt: Quay.");
    }

    public void turnOff() {
        System.out.println("Quạt: Dừng.");
    }
}

// Điều hòa
class AirConditioner implements Device {
    public void turnOn() {
        System.out.println("Điều hòa: Bật.");
    }

    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}

// Factory abstract
abstract class DeviceFactory {
    public abstract Device createDevice();
}

// Factory đèn
class LightFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light();
    }
}

// Factory quạt
class FanFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}

// Factory điều hòa
class AirConditionerFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Device> devices = new ArrayList<>();

        while (true) {
            System.out.println("\n1. Kết nối");
            System.out.println("2. Tạo thiết bị");
            System.out.println("3. Bật");
            System.out.println("4. Tắt");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    HardwareConnection.getInstance(); // gọi singleton
                    break;

                case 2:
                    System.out.println("1. Đèn  2. Quạt  3. Điều hòa");
                    int type = sc.nextInt();

                    DeviceFactory factory = null;

                    if (type == 1) factory = new LightFactory();
                    else if (type == 2) factory = new FanFactory();
                    else if (type == 3) factory = new AirConditionerFactory();
                    else {
                        System.out.println("Sai!");
                        continue;
                    }

                    devices.add(factory.createDevice()); // tạo qua factory
                    break;

                case 3:
                    if (devices.isEmpty()) {
                        System.out.println("Chưa có!");
                        break;
                    }
                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println((i + 1) + ". Device");
                    }
                    devices.get(sc.nextInt() - 1).turnOn();
                    break;

                case 4:
                    if (devices.isEmpty()) {
                        System.out.println("Chưa có!");
                        break;
                    }
                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println((i + 1) + ". Device");
                    }
                    devices.get(sc.nextInt() - 1).turnOff();
                    break;

                case 0:
                    return;
            }
        }
    }
}
