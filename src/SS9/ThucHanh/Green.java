package session9.ThucHanh;

public class Green implements TrafficLightState{
    public TrafficLightState next() { return new Yellow(); }
    public String color() { return "GREEN"; }
}
