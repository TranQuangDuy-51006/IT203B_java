package session9.ThucHanh;

public class Yellow implements TrafficLightState{
    public TrafficLightState next() { return new Red(); }
    public String color() { return "YELLOW"; }
}
