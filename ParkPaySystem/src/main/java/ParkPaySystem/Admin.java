package main.java.ParkPaySystem;

import main.java.ParkPaySystem.Park;

public class Admin {
    Park[] listOfParks;

    public Admin(Park[] parks){
        listOfParks = parks;
    }

    public Park[] listParks(){
        return this.listOfParks;
    }
}
