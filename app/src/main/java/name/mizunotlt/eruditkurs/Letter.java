package name.mizunotlt.eruditkurs;

public class Letter {

    private char letter;
    private int countScore;
    private int count;

    Letter(char letter, int countScore, int count){
        this.letter = letter;
        this.countScore = countScore;
        this.count = count;
    }


    public char getLetter() {
        return letter;
    }
    public int getCountScore() {
        return countScore;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
