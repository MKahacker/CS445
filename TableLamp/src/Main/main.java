package Main;

import Button.*;
import Lamp.TableLamp;
import Lightbulb.*;

public class main {
    public static void main(String[] args){
        Lightbulb myLightbulb = new Lightbulb();
        PushDownButton myButton = new PushDownButton(myLightbulb);
        TableLamp myTableLamp = new TableLamp(myButton);

        myButton.PushButton();
        myButton.PushButton();

        myTableLamp.switchOn();
        myTableLamp.switchOff();

    }
}
