package Application;

public class Fly extends Creature {
    public Fly (String name){
        super(name);
    }

    public void move(){
        fly();
    }

    public void fly(){
        System.out.println(super.getName() + " " + getClass().getSimpleName() + " is buzzing around in flight.");
    }

    @Override
    public void eat(Thing aThing) {
        if (aThing instanceof Creature){
            System.out.println("Fly doesn't eat Creatures");
        }else if(aThing instanceof Thing){
            super.eat(aThing);
        }else {
            System.out.println("Fly doesn't eat things that aren't Thing");
        }
    }
}
