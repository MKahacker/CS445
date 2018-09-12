package Application;

public abstract class Creature extends Thing{
    private String stomache = null;

    public Creature(String name){
        super(name);
    }
    
    public abstract void move();

    public void eat(Thing aThing){
        stomache = aThing.getName();
        System.out.println(super.getName() + " has just eaten a " + aThing.getName());
    }
    
    public void whatDidYouEat(){
        if(stomache != null) {
            System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " has eaten a " + stomache);
        }else{
            System.out.println(super.getName() + " " + this.getClass().getSimpleName() + " has had nothing to eat!");
        }
    }

}