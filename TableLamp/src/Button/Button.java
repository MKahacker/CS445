package Button;

import Lightbulb.Lightbulb;

public class Button {

    Lightbulb lightbulb_b;

    public Button(){
        lightbulb_b = new Lightbulb();
    }

    public void switchOn(){
        System.out.println("Button switched to ON");
        lightbulb_b.on();
    }

    public void switchOff(){
        System.out.println("Button switched to OFF");
        lightbulb_b.off();
    }
}
