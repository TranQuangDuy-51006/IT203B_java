package SS8.B2;

import java.util.*;

// Interface cảm biến mới
interface TemperatureSensor {
    double getTemperatureCelsius();
}

// Cảm biến cũ (độ F)
class OldThermometer {
    public int getTemperatureFahrenheit() {
        return 78; // giả lập
    }
}

// Adapter - đổi F -> C
class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldThermometer;

    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
    }

    public double getTemperatureCelsius() {
        int f = oldThermometer.getTemperatureFahrenheit();
        return (f - 32) * 5.0 / 9;
    }
}

// Thiết bị
class Light {
    void off() {
        System.out.println("FACADE: Tắt đèn");
    }
}

class Fan {
    void off() {
        System.out.println("FACADE: Tắt quạt");
    }

    void low() {
        System.out.println("FACADE: Quạt chạy tốc độ thấp");
    }
}

class AirConditioner {
    void off() {
        System.out.println("FACADE: Tắt điều hòa");
    }

    void setTemp(int t) {
        System.out.println("FACADE: Điều hòa set " + t + "°C");
    }
}

// Facade - điều khiển đơn giản
class SmartHomeFacade {
    private Light light = new Light();
    private Fan fan = new Fan();
    private AirConditioner ac = new AirConditioner();
    private TemperatureSensor sensor;

    public SmartHomeFacade(TemperatureSensor sensor) {
        this.sensor = sensor;
    }

    public void leaveHome() {
        light.off();
        fan.off();
        ac.off();
    }

    public void sleepMode() {
        light.off();
        ac.setTemp(28);
        fan.low();
    }

    public void getCurrentTemperature() {
        double temp = sensor.getTemperatureCelsius();
        System.out.printf("Nhiệt độ hiện tại: %.1f°C\n", temp);
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // dùng adapter cho cảm biến cũ
        TemperatureSensor sensor = new ThermometerAdapter(new OldThermometer());
        SmartHomeFacade home = new SmartHomeFacade(sensor);

        while (true) {
            System.out.println("\n1. Xem nhiệt độ");
            System.out.println("2. Rời nhà");
            System.out.println("3. Ngủ");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    home.getCurrentTemperature();
                    break;
                case 2:
                    home.leaveHome();
                    break;
                case 3:
                    home.sleepMode();
                    break;
                case 0:
                    return;
            }
        }
    }
}
