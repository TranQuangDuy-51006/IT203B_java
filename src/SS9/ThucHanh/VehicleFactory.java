package session9.ThucHanh;

import java.util.Random;

public class VehicleFactory {
    static int id = 0; // đếm số xe

    public static Vehicle create(Intersection i) {
        id++;

        // random loại xe
        if (new Random().nextInt(3) == 0) {
            // 1/3 xác suất là xe cứu thương
            return new PriorityVehicle(" Xe cứu thương " + id, i);
        }

        // còn lại là xe thường
        return new StandardVehicle("Xe thường " + id, i);
    }
}
