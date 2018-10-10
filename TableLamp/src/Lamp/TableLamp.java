package Lamp;

import Button.*;


public class TableLamp {
    AbstractButton myButton;

    public TableLamp(AbstractButton button){
        myButton = button;
    }

    public void switchOn(){
        System.out.println("Table Lamp switched On");
    }

    public void switchOff(){
        System.out.println("Table Lamp switched Off");
    }

}
