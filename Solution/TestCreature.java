package Solution;

import java.lang.String;

public class TestCreature{
    private static int CREATURE_COUNT = 6;
    private static int THING_COUNT = 10;

    public static void main(String[] args){
        Thing[] things;
        things = new Thing[THING_COUNT];
        for(int i=0; i < THING_COUNT; i++){
            things[i] = new Thing("rem");
        }

        for(int j = 0; j < THING_COUNT; j++){
            things[j].toString();
        }
    }
}