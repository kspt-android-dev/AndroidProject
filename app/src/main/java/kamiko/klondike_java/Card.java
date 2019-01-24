package kamiko.klondike_java;

import android.widget.ImageView;

import java.io.Serializable;

public class Card implements Serializable {

    int value;
    private Suit suit;
    private boolean isFaceUp;
    private ImageView imageView;
    int image;
    private int bucket;
    int position;
    private boolean topCard;

    enum Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES;

        public boolean isBlack() {
            return (this == CLUBS) || (this == SPADES);
        }

        public boolean isRed() {
            return ((this == HEARTS) || (this == DIAMONDS));
        }

        public int getHouse() {
            switch (this) {
                case DIAMONDS:
                    return 9;
                case CLUBS:
                    return 10;
                case HEARTS:
                    return 11;
                case SPADES:
                    return 12;
                default:
                    return -1;
            }
        }
    }

    Card (int value, Suit suit, boolean isFaceUp, ImageView imageView, int image, int bucket, int position, boolean topCard) {
        this.value = value;
        this.suit = suit;
        this.isFaceUp = isFaceUp;
        this.imageView = imageView;
        this.image = image;
        this.bucket = bucket;
        this.position = position;
        this.topCard = topCard;
    }

    boolean isTopCard() {
        return topCard;
    }

    void setTopCard(boolean topCard) {
        this.topCard = topCard;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int getBucket() {
        return bucket;
    }

    void setBucket(int bucket) {
        this.bucket = bucket;
    }

    public int getValue() {
        return value;
    }

    Suit getSuit() {
        return suit;
    }

    boolean isFaceUp() {
        return isFaceUp;
    }

    ImageView getImageView() {
        return imageView;
    }

    public int getImage() {
        return image;
    }

    public void setValue(int value) {
        this.value = value;
    }

    void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
