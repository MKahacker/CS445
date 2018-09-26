import java.util.Random;

public class ImprovedRandom extends Random{
    public ImprovedRandom(){
        super();
    }

    public ImprovedRandom(long seed){
        super(seed);
    }

    public int nextIntBounded(int lower, int upper){
        int rand = this.nextInt(upper);
        if(rand < lower){
            rand = rand + lower;
        }

        return rand;
    }
}
