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

    public static int paymentType(String type){
        String formatedType = type.toLowerCase();
        if(formatedType.equals("car")){
            return 1;
        }else if(formatedType.equals("rv")){
            return 2;
        }else if(formatedType.equals("motorcycle")){
            return 0;
        }else{
            return -1;
        }
    }

}
