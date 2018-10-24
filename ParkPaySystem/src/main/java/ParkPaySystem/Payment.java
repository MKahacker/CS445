package ParkPaySystem;

public abstract class Payment {
    double inStateFee;
    double outStateFee;

    public double getInStateFee(){
      return this.inStateFee;
    }
    public double getOutStateFee(){
        return this.outStateFee;
    }

}
