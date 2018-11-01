package ParkPaySystem;

public class Vehicle {
    private String state;
    private String plate;
    private String type;

    public Vehicle(String state, String plate, String instanceType){
        this.state = state;
        this.plate = plate;
        this.type = instanceType;
    }

    public String getState() {
        return this.state;
    }

    public String getPlate() {
        return this.plate;
    }

    public String getType() {
        return this.type;
    }
}
