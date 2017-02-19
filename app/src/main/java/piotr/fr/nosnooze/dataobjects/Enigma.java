package piotr.fr.nosnooze.dataobjects;

/**
 * Created by Piotr on 07/02/2015.
 */
public class Enigma {

    String text;
    int expected;

    public Enigma(String text, int expected){
        this.text=text;
        this.expected=expected;
    }

    public boolean isSolved(int proposal){
        return proposal==expected;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text + " = " + expected;
    }
}
