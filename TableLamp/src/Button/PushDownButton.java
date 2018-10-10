package Button;

import Lightbulb.InterfaceLightBulb;

public class PushDownButton extends AbstractButton {

    public PushDownButton(InterfaceLightBulb lightbulb){
        myLightBulb = lightbulb;
    }

    public void PushButton(){
        myLightBulb.switchStatus();
        System.out.println("Button push down");
    }
}
