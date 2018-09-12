package Application;

public class Tiger extends Creature{
    public Tiger(String name){
        super(name);
    }

    @Override
    public void whatDidYouEat() {
        super.whatDidYouEat();
    }

    @Override
    public void eat(Thing aThing) {
        super.eat(aThing);
    }

    public void move(){
        System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " has just pounced.");
    }

}