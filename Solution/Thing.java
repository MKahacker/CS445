package Solution;

import java.lang.String;

public class Thing{
    private String name;

    public Thing(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return "name: " + name;
    }
}

