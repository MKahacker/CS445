package Application;

public class Ant extends Creature {
    public Ant (String name){
        super(name);
    }

    public void eat(Thing aThing){
        super.eat(aThing);
    }

    public void move(){
        System.out.println(super.getName() + " " + getClass().getSimpleName() + " is crawling around.");
    }

    public void whatDidYouEat(){
        super.whatDidYouEat();
    }

}
