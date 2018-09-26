import java.util.StringTokenizer;

public class ImprovedStringTokenizer extends StringTokenizer {
    private String stringText;

    public ImprovedStringTokenizer(String words){
        super(words);
        stringText = words;
    }

    public String[] returnWordsInAnArray(){
        String[] stringArray = stringText.split("\\s");
        return stringArray;
    }

}
