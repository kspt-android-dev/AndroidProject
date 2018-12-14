package name.mizunotlt.eruditkurs;

public class Letter {

    private char letter;
    private int countScore;
    private int count;

    public Letter(char letter, int countScore, int count){
        this.letter = letter;
        this.countScore = countScore;
        this.count = count;
    }


    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getCountScore() {
        return countScore;
    }

    public void setCountScore(int countScore) {
        this.countScore = countScore;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
