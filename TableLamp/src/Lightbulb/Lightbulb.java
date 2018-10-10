package Lightbulb;

public class Lightbulb implements InterfaceLightBulb {
    boolean lightOn;
    public Lightbulb(){
        lightOn = false;
    }

    public void on(){
        lightOn = true;
        System.out.println("Lightbulb on");
    }

    public void off(){
        lightOn = false;
        System.out.println("Lightbulb off");
    }

    public void switchStatus(){
        lightOn = !lightOn;
    }

}
