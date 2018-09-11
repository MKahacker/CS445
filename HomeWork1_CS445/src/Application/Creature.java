package Application;

public abstract class Creature extends Thing{
    private String stomache;

    public Creature(String name){
        super(name);
    }
    
    public abstract void move();

    public void eat(Thing aThing){
        stomache = aThing.getName();
        System.out.println(super.getName() + " has just eaten a " + aThing.getName());
    }
    
    public void whatDidYouEat(){
        System.out.println(stomache);
        
    }

}