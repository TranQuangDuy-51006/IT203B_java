package session9.ThucHanh;

public class PriorityVehicle extends Vehicle{
    public PriorityVehicle(String name, Intersection i) {
        super(name, i);
    }

    // xe cứu thương → được ưu tiên
    @Override
    public boolean isPriority() {
        return true;
    }
}
