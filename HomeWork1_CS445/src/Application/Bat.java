package Application;

public class Bat extends Creature{
    public Bat(String name){
        super(name);
    }

    public void eat(Thing athing){
        
    }

    public void Fly(){
        System.out.println(super.getName() + " the Bat " + "is swooping through the dark.");
    }

    public void move(){
        Fly();        
    }

}