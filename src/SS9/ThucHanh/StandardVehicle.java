package session9.ThucHanh;

public class StandardVehicle extends Vehicle{
    public StandardVehicle(String name, Intersection i) {
        super(name, i);
    }

    // xe thường → không ưu tiên
    @Override
    public boolean isPriority() {
        return false;
    }
}
