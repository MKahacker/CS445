package Application;

public class Bat extends Creature implements Flyer{
    public Bat(String name){
        super(name);
    }

    public void fly(){
        System.out.println(super.getName() + " the Bat " + "is swooping through the dark.");
    }

    public void move(){
        fly();
    }

}