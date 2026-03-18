package session9.ThucHanh;

public interface TrafficLightState {
    TrafficLightState next();
    String color();
}
