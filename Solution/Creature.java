package Solution;

import java.lang.String;

public abstract class Creature extends Thing{
    private String stomache;

    public Creature(String name){
        super(name);
    }
    
    public abstract void move();

    public void eat(Thing aThing){
        stomache = aThing.name;
        System.out.println(super.name + "has just eaten a" + aThing.name);
    }
    
    public void whatDidYouEat(){
        System.out.println(stomache);
        
    }

}