package Application;

public class Ant extends Creature {
    public Ant (String name){
        super(name);
    }

    public void move(){
        System.out.println(super.getName() + " " + getClass().getSimpleName() + " is crawling around.");
    }

}
