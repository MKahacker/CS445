package Application;

public class Bat extends Creature implements Flyer{
    public Bat(String name){
        super(name);
    }

    public void move(){
        fly();
    }

    public void fly(){
        System.out.println(super.getName() + " the Bat " + "is swooping through the dark.");
    }

    @Override
    public void whatDidYouEat() {
        super.whatDidYouEat();
    }

    @Override
    public void eat(Thing aThing) {
        if(aThing.getClass().getSuperclass().getSimpleName().equals("Creature")) {
            super.eat(aThing);
        }else if (aThing.getClass().getSimpleName().equals("Thing")){
            System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " won't eat a " + aThing.toString());
        }else{

        }
    }
}