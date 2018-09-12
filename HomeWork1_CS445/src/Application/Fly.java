package Application;

public class Fly extends Creature implements Flyer {
    public Fly (String name){
        super(name);
    }

    public void move(){
        fly();
    }

    public void fly(){
        System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " is buzzing around in flight.");
    }

    @Override
    public void whatDidYouEat() {
        super.whatDidYouEat();
    }

    @Override
    public void eat(Thing aThing) {
        if(aThing.getClass().getSimpleName().equals("Thing")){
            super.eat(aThing);
        }else {
            System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " won't eat a " + aThing.getClass().getSimpleName());
        }
    }
}
