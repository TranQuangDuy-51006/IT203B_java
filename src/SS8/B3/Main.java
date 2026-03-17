package SS8.B3;

import java.util.*;

// Command interface
interface Command {
    void execute();
    void undo();
}

// ===== DEVICE =====

// Đèn
class Light {
    void on() { System.out.println("Đèn: Bật"); }
    void off() { System.out.println("Đèn: Tắt"); }
}

// Quạt
class Fan {
    void on() { System.out.println("Quạt: Bật"); }
    void off() { System.out.println("Quạt: Tắt"); }
}

// Điều hòa
class AirConditioner {
    private int temp = 25;

    void setTemp(int t) {
        temp = t;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    int getTemp() { return temp; }
}

// ===== COMMAND =====

// Đèn bật
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) { this.light = light; }

    public void execute() { light.on(); }

    public void undo() {
        light.off();
        System.out.println("Undo: Đèn Tắt");
    }
}

// Đèn tắt
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) { this.light = light; }

    public void execute() { light.off(); }

    public void undo() {
        light.on();
        System.out.println("Undo: Đèn Bật");
    }
}

// Quạt bật
class FanOnCommand implements Command {
    private Fan fan;

    public FanOnCommand(Fan fan) { this.fan = fan; }

    public void execute() { fan.on(); }

    public void undo() {
        fan.off();
        System.out.println("Undo: Quạt Tắt");
    }
}

// Quạt tắt
class FanOffCommand implements Command {
    private Fan fan;

    public FanOffCommand(Fan fan) { this.fan = fan; }

    public void execute() { fan.off(); }

    public void undo() {
        fan.on();
        System.out.println("Undo: Quạt Bật");
    }
}

// Điều hòa set nhiệt độ
class ACSetTemperatureCommand implements Command {
    private AirConditioner ac;
    private int newTemp;
    private int oldTemp;

    public ACSetTemperatureCommand(AirConditioner ac, int temp) {
        this.ac = ac;
        this.newTemp = temp;
    }

    public void execute() {
        oldTemp = ac.getTemp(); // lưu trạng thái cũ
        ac.setTemp(newTemp);
    }

    public void undo() {
        ac.setTemp(oldTemp);
        System.out.println("Undo: Điều hòa: Nhiệt độ = " + oldTemp);
    }
}

// ===== REMOTE =====

class RemoteControl {
    private Map<Integer, Command> slots = new HashMap<>();
    private Stack<Command> history = new Stack<>();

    // gán nút
    void setCommand(int button, Command command) {
        slots.put(button, command);
        System.out.println("Đã gán " + command.getClass().getSimpleName() + " cho nút " + button);
    }

    // bấm nút
    void pressButton(int button) {
        Command cmd = slots.get(button);
        if (cmd != null) {
            cmd.execute();
            history.push(cmd); // lưu để undo
        } else {
            System.out.println("Chưa gán nút!");
        }
    }

    // undo
    void undo() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
        } else {
            System.out.println("Không có gì để undo!");
        }
    }
}

// ===== MAIN =====

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        RemoteControl remote = new RemoteControl();

        while (true) {
            System.out.println("\n1. Gán nút");
            System.out.println("2. Nhấn nút");
            System.out.println("3. Undo");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Chọn nút:");
                    int btn = sc.nextInt();

                    System.out.println("1.Light ON  2.Light OFF  3.Fan ON  4.Fan OFF  5.AC Set Temp");
                    int type = sc.nextInt();

                    Command cmd = null;

                    if (type == 1) cmd = new LightOnCommand(light);
                    else if (type == 2) cmd = new LightOffCommand(light);
                    else if (type == 3) cmd = new FanOnCommand(fan);
                    else if (type == 4) cmd = new FanOffCommand(fan);
                    else if (type == 5) {
                        System.out.println("Nhập nhiệt độ:");
                        int t = sc.nextInt();
                        cmd = new ACSetTemperatureCommand(ac, t);
                    }

                    if (cmd != null)
                        remote.setCommand(btn, cmd);

                    break;

                case 2:
                    System.out.println("Nhấn nút:");
                    remote.pressButton(sc.nextInt());
                    break;

                case 3:
                    remote.undo();
                    break;

                case 0:
                    return;
            }
        }
    }
}