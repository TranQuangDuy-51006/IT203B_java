package session9.ThucHanh;

public class Red implements TrafficLightState{
    public TrafficLightState next() { return new Green(); }
    public String color() { return "RED"; }
}
