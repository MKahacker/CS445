package Application;

public class Tiger extends Creature{
    public Tiger(String name){
        super(name);
    }
    
    public void move(){
        System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " has just pounced");
    }

}