package Button;

import Lightbulb.*;

public class Button extends AbstractButton{

    public Button(InterfaceLightBulb lightBulb){
        myLightBulb = lightBulb;
    }

    public void switchOn(){
        System.out.println("Button switched to ON");
        myLightBulb.on();
    }

    public void switchOff(){
        System.out.println("Button switched to OFF");
        myLightBulb.off();
    }
}
