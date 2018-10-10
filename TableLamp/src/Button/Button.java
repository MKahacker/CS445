package Button;

import Lightbulb.Lightbulb;

public class Button extends AbstractButton{

    public Button(){
        myLightBulb = new Lightbulb();
    }

    public void switchOn(){
        System.out.println("Button switched to ON");
    }

    public void switchOff(){
        System.out.println("Button switched to OFF");
    }
}
