package kamiko.klondike_java;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

//////////////////////////////////
// Расположение на поле
//////////////////////////////////
//  0     2 3 4 5 6 7 8       9 DIAMONDS
//  1                         10 CLUBS
//                            11 HEARTS
//                            12 SPADES
//////////////////////////////////

    int[] images = new int[]{R.drawable.c1_1, R.drawable.c2_1, R.drawable.c3_1, R.drawable.c4_1, // DIAMONDS
            R.drawable.c5_1, R.drawable.c6_1, R.drawable.c7_1, R.drawable.c8_1, R.drawable.c9_1,
            R.drawable.c10_1, R.drawable.c11_1, R.drawable.c12_1, R.drawable.c13_1,
            R.drawable.c1_2, R.drawable.c2_2, R.drawable.c3_2, R.drawable.c4_2, // CLUBS
            R.drawable.c5_2, R.drawable.c6_2, R.drawable.c7_2, R.drawable.c8_2,
            R.drawable.c9_2, R.drawable.c10_2, R.drawable.c11_2, R.drawable.c12_2, R.drawable.c13_2,
            R.drawable.c1_3, R.drawable.c2_3, R.drawable.c3_3, R.drawable.c4_3, R.drawable.c5_3, // HEARTS
            R.drawable.c6_3, R.drawable.c7_3, R.drawable.c8_3, R.drawable.c9_3, R.drawable.c10_3,
            R.drawable.c11_3, R.drawable.c12_3, R.drawable.c13_3,
            R.drawable.c1_4, R.drawable.c2_4, R.drawable.c3_4, R.drawable.c4_4, R.drawable.c5_4, // SPADES
            R.drawable.c6_4, R.drawable.c7_4, R.drawable.c8_4, R.drawable.c9_4,
            R.drawable.c10_4, R.drawable.c11_4, R.drawable.c12_4, R.drawable.c13_4,
            R.drawable.shirt, R.drawable.rewinder, R.drawable.house_diamonds, R.drawable.house_clubs, R.drawable.house_hearts, R.drawable.house_spades,
            R.drawable.kingplace}; //

    int COUNT_OF_PARTS_X;
    int COUNT_OF_PARTS_Y;
    int CARD_WIDTH;
    int CARD_HEIGHT;
    int INDENT_DECK;
    int INDENT_HOUSES_Y;
    int INDENT_HOUSES_X = 25;
    int INDENT_START = 3;
    int INDENT_CARD_X = 2;
    int INDENT_STEPS = 5;
    int INDENT_COUNTER = 6;
    int INDENT_INF = 8;
    int INDENT_CLOCK = 4;

    int SIZE_IMG = 70;
    int COUNT_OF_CARDS = 52;
    int COUNT_OF_SUITS = 4;
    int COUNT_OF_VALUES = 13;
    int FIRST_GAME_BUCKET = 2;
    int LAST_GAME_BUCKET = 8;
    int DIAMOND_HOUSE = 9;
    int CLUB_HOUSE = 10;
    int HEART_HOUSE = 11;
    int SPADE_HOUSE = 12;
    int LEAD_DECK_CLOSE_BUCKET = 0;
    int LEAD_DECK_OPEN_BUCKET = 1;
    int VALUE_OF_KING = 12;
    int START_NUMBER_OF_HOUSES_PIC = 54;
    int PIC_OF_KINGPLACE = 58;

    int HEIGHT, WIDTH, conventUnitX, conventUnitY, counter;
    Card[] deck = new Card[COUNT_OF_CARDS];
    ConstraintLayout gameFieldLayout;
    FrameLayout.LayoutParams layoutParams;
    Chronometer mChronometer;
    TextView steps, counterTxt;
    long startTime;

    int[] imgViews = new int[64]; // 64 - кол-во используемых изображений
    private static final String TAG = "MY_TAG";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        WIDTH = metricsB.widthPixels;
        HEIGHT = metricsB.heightPixels;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CARD_WIDTH = 4;
            CARD_HEIGHT = 5;
            COUNT_OF_PARTS_X = 30;
            COUNT_OF_PARTS_Y = 28;
            INDENT_DECK = 9;
            INDENT_HOUSES_Y = 6;
        } else {
            CARD_WIDTH = 2;
            CARD_HEIGHT = 3;
            COUNT_OF_PARTS_X = 30;
            COUNT_OF_PARTS_Y = 28;
            INDENT_DECK = 5;
            INDENT_HOUSES_Y = 3;
        }

        conventUnitX = WIDTH / COUNT_OF_PARTS_X;
        conventUnitY = HEIGHT / COUNT_OF_PARTS_Y;

        layoutParams = new FrameLayout.LayoutParams(CARD_WIDTH * conventUnitX, CARD_HEIGHT * conventUnitY);
        gameFieldLayout = findViewById(R.id.activity_main);

        final Button replay = findViewById(R.id.newGame);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.newGame) {
                    Log.d(TAG, "Replay clicked");
                    makeGame(null);
                }
            }
        });

        mChronometer = findViewById(R.id.chronometer);
        mChronometer.setX(WIDTH / INDENT_CLOCK); // выставляем по х на середну первой половины
        mChronometer.setY(conventUnitY);

        steps = findViewById(R.id.steps);
        steps.setX(WIDTH / INDENT_INF * INDENT_STEPS); //
        steps.setY(conventUnitY);

        counterTxt = findViewById(R.id.counter);
        counterTxt.setX(WIDTH / INDENT_INF * INDENT_COUNTER);
        counterTxt.setY(conventUnitY);

        makeGame(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("deck", deck);
        mChronometer.stop();
        long mChronometerBase = mChronometer.getBase();
        outState.putLong("mChronometerBase", mChronometerBase);
        outState.putInt("counter", counter);
        outState.putLong("startTime", startTime);
        Log.d(TAG, "onSaveInstanceState()");
    }

    @SuppressLint("SetTextI18n")
    public void makeGame(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG, "Restore saved instance of gameboard");
            deck = (Card[]) savedInstanceState.getSerializable("deck");
            setRewinder();
            setHouses();
            for (int i = 0; i < COUNT_OF_CARDS; i++) {
                if (deck[i].getImageView().getParent() != null) {
                    ((ViewGroup) deck[i].getImageView().getParent()).removeView(deck[i].getImageView());
                }
            }
            deal();
            long mChronometerBase = savedInstanceState.getLong("mChronometerBase");
            mChronometer.setBase(mChronometerBase);
            mChronometer.start();
            startTime = savedInstanceState.getLong("startTime");
            counter = savedInstanceState.getInt("counter");
            counterTxt.setText(counter + "");

        } else {
            Log.d(TAG, "Make gameboard");
            for (int i = 0; i < COUNT_OF_CARDS; i++) {
                if (deck[i] != null) {
                    if (deck[i].getImageView().getParent() != null) {
                        ((ViewGroup) deck[i].getImageView().getParent()).removeView(deck[i].getImageView());
                    }
                    deck[i] = null;
                }
            }
            makeDeck();
            Collections.shuffle(Arrays.asList(deck));
            makeGameboard();
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            startTime = System.nanoTime();
            counter = 0;
            counterTxt.setText(counter + "");
        }
    }

    public void makeDeck() {
        int index = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (int n = 0; n < COUNT_OF_VALUES; n++, index++) {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[COUNT_OF_CARDS], SIZE_IMG, SIZE_IMG));
                generateId(imageView, index);
                deck[index] = new Card(n, suit, false, imageView, images[index], 0, -1, false);
                deck[index].getImageView().setTag(deck[index]);
            }
        }
    }

    public void makeGameboard() {

        int k = 0;
        Log.d(TAG, "Значение : Масть : Ящик : Позиция : Лицом вверх : id : Верхняя ли");
        for (int i = FIRST_GAME_BUCKET; i <= LAST_GAME_BUCKET; i++) { // игровые бакеты
            setKingPlace((i * INDENT_START - INDENT_CARD_X) * conventUnitX, INDENT_START * conventUnitY, i);

            for (int j = FIRST_GAME_BUCKET; j <= i; j++, k++) { // позиции
                deck[k].setBucket(i);
                deck[k].setPosition(j - FIRST_GAME_BUCKET);
                setCardImageView(deck[k].getImageView(), (i * INDENT_START - INDENT_CARD_X) * conventUnitX,  (j + 1) * conventUnitY);
                if (i == j) {
                    flip(deck[k], true);
                    deck[k].setTopCard(true);
                }
                gameFieldLayout.addView(deck[k].getImageView());
                deck[k].getImageView().setTag(deck[k]);
                Log.d(TAG, (deck[k].getValue() + 1) + " : " + deck[k].getSuit() + " : " + deck[k].getBucket()
                        + " : " + deck[k].getPosition() + " : " + deck[k].isFaceUp() + " : " + deck[k].getImageView().getId() + " : " + deck[k].isTopCard());
            }
        }

        setRewinder();
        setHouses();

        int position = 0;
        for (int i = 0; i < COUNT_OF_CARDS; i++) { // ведущая колода
            if (deck[i].getBucket() == LEAD_DECK_CLOSE_BUCKET) {
                setCardImageView(deck[i].getImageView(), conventUnitX, INDENT_START * conventUnitY);
                gameFieldLayout.addView(deck[i].getImageView());
                deck[i].setPosition(position);
                deck[i].getImageView().setTag(deck[i]);
                final int finalI = i;
                deck[i].getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickCloseLeadDeck(finalI);
                    }
                });
                position++;
            }
        }
    }

    public void deal() {
        for (int i = LEAD_DECK_CLOSE_BUCKET; i <= LAST_GAME_BUCKET; i++) {
            int position = 0;
            if (i != LEAD_DECK_CLOSE_BUCKET && i != LEAD_DECK_OPEN_BUCKET)
                setKingPlace((i * INDENT_START - INDENT_CARD_X) * conventUnitX, INDENT_START * conventUnitY, i);
            for (int j = 0; j < COUNT_OF_CARDS; j++) {
                if (deck[j].getBucket() == i && deck[j].getPosition() == position) {
                    if (deck[j].isFaceUp()) {
                        flip(deck[j], true);
                        if (deck[j].getBucket() == LEAD_DECK_OPEN_BUCKET)
                            setCardImageView(deck[j].getImageView(), conventUnitX, INDENT_DECK * conventUnitY);
                        else
                            setCardImageView(deck[j].getImageView(), (i * INDENT_START - INDENT_CARD_X) * conventUnitX, (deck[j].getPosition() + INDENT_START) * conventUnitY);
                        gameFieldLayout.addView(deck[j].getImageView());
                        position++;
                        j = 0;
                    } else {
                        flip(deck[j], false);
                        if (deck[j].getBucket() == LEAD_DECK_CLOSE_BUCKET) {
                            setCardImageView(deck[j].getImageView(), conventUnitX, INDENT_START * conventUnitY);
                            gameFieldLayout.addView(deck[j].getImageView());
                            final int finalI = j;
                            deck[j].getImageView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onClickCloseLeadDeck(finalI);
                                }
                            });
                        } else {
                            setCardImageView(deck[j].getImageView(),  (i * INDENT_START - INDENT_CARD_X) * conventUnitX, (deck[j].getPosition() + INDENT_START) * conventUnitY);
                            gameFieldLayout.addView(deck[j].getImageView());
                            j = 0;
                        }
                        position++;
                    }
                }
            }
        }

        int k = INDENT_START;
        for (int i = DIAMOND_HOUSE; i <= SPADE_HOUSE; i++) {
            int position = 0;
            for (int j = 0; j < COUNT_OF_CARDS; j++) {
                if (deck[j].getBucket() == i && deck[j].getPosition() == position) {
                    setCardImageView(deck[j].getImageView(), INDENT_HOUSES_X * conventUnitX, conventUnitY * k);
                    deck[j].getImageView().setOnDragListener(new View.OnDragListener() {
                        @Override
                        public boolean onDrag(View v, DragEvent event) {
                            return onDragCard(v, event);
                        }
                    });
                    gameFieldLayout.addView(deck[j].getImageView());
                    position++;
                    j = 0;
                }
            }
            k = k + INDENT_HOUSES_Y;
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onClickCloseLeadDeck(int finalI) {
        deck[finalI].setPosition(numberOfCardsInBucket(LEAD_DECK_OPEN_BUCKET));
        deck[finalI].setBucket(LEAD_DECK_OPEN_BUCKET);
        flip(deck[finalI], true);
        deck[finalI].getImageView().setOnClickListener(null);
        if (deck[finalI].getImageView().getParent() != null) {
            ((ViewGroup) deck[finalI].getImageView().getParent()).removeView(deck[finalI].getImageView());
        }
        setCardImageView(deck[finalI].getImageView(), conventUnitX, INDENT_DECK * conventUnitY);
        if (deck[finalI].getImageView().getParent() != null) {
            gameFieldLayout.addView(deck[finalI].getImageView());
        } else gameFieldLayout.addView(deck[finalI].getImageView());
        deck[finalI].getImageView().setTag(deck[finalI]);
        updateCounter();
    }

    public boolean onTouchCard(View v, MotionEvent event, Card card) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            Log.d(TAG, "Взяли карту: " + (card.getValue() + 1) + " : " + card.getSuit() + " : " + card.getBucket()
                    + " : " + card.getPosition() + " : " + card.isFaceUp() + " : " + card.getImageView().getId() + " : " + card.isTopCard());
            return true;
        } else return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void rewind() {
        for (int j = 0; j < COUNT_OF_CARDS; j++) {
            if (deck[j].getBucket() == LEAD_DECK_OPEN_BUCKET) {
                deck[j].setPosition(numberOfCardsInBucket(LEAD_DECK_CLOSE_BUCKET));
                deck[j].setBucket(LEAD_DECK_CLOSE_BUCKET);
                flip(deck[j], false);
                deck[j].getImageView().setOnTouchListener(null);
                final int finalJ = j;
                deck[j].getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickCloseLeadDeck(finalJ);
                    }
                });
                gameFieldLayout.removeView(deck[j].getImageView());
                setCardImageView(deck[j].getImageView(), conventUnitX, INDENT_START * conventUnitY);
                gameFieldLayout.addView(deck[j].getImageView());
                deck[j].getImageView().setTag(deck[j]);
            }
        }
        updateCounter();
    }

    @SuppressLint("ResourceType")
    public void setRewinder() {
        ImageView rewinder = new ImageView(getApplicationContext());
        rewinder.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[COUNT_OF_CARDS + 1], SIZE_IMG, SIZE_IMG));
        setCardImageView(rewinder, conventUnitX, INDENT_START * conventUnitY);
        gameFieldLayout.addView(rewinder);
        generateId(rewinder, COUNT_OF_CARDS);
        rewinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewind();
            }
        });
    }

    public void setKingPlace(int x, int y, final int bucket) {
        final ImageView kingPlace = new ImageView(getApplicationContext());
        kingPlace.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[PIC_OF_KINGPLACE], SIZE_IMG, SIZE_IMG));
        setCardImageView(kingPlace, x, y);
        gameFieldLayout.addView(kingPlace);
        generateId(kingPlace,COUNT_OF_CARDS + bucket - 1);
        kingPlace.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        View view = (View) event.getLocalState();
                        Card card = (Card) view.getTag();
                        gameFieldLayout.removeView(card.getImageView());
                        setCardImageView(card.getImageView(), v.getX(), v.getY());
                        makeTopCard(card.getBucket(), card.getPosition() - 1); // сделать верхней ту карту, которая была под переносимой
                        if ((card.getValue() == VALUE_OF_KING && card.isTopCard()) || (card.getValue() == VALUE_OF_KING && card.getBucket() == 1)) { // если карта короля верхняя на поле или в находится в колоде
                            card.setBucket(bucket);
                            card.setPosition(0);
                            card.setTopCard(true);
                            gameFieldLayout.addView(card.getImageView());
                            card.getImageView().setTag(card);
                        } else if (card.getValue() == VALUE_OF_KING && !card.isTopCard()) { // иначе, если карта короля не верхняя на поле
                            transferPack(card, bucket, 0);
                        }
                        updateCounter();
                        Log.d(TAG, "Успешно положили короля на место короля");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void setHouses() {
        int k = INDENT_START;
        int i = START_NUMBER_OF_HOUSES_PIC;
        for (Card.Suit suit : Card.Suit.values()) {
            ImageView house = new ImageView(getApplicationContext());
            house.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[i], SIZE_IMG, SIZE_IMG));
            setCardImageView(house, INDENT_HOUSES_X * conventUnitX, conventUnitY * k);
            gameFieldLayout.addView(house);
            house.setTag(suit);
            generateId(house, i + 6);
            house.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DROP:
                            View view = (View) event.getLocalState();
                            Card card = (Card) view.getTag();
                            Card.Suit houseSuit = (Card.Suit) v.getTag();
                            if (card.getSuit() == houseSuit && card.getValue() == 0) { // если подходит масть и это туз
                                gameFieldLayout.removeView(card.getImageView());
                                setCardImageView(card.getImageView(), v.getX(), v.getY());
                                makeTopCard(card.getBucket(), card.getPosition() - 1);
                                card.setBucket(houseSuit.getHouse());
                                card.setPosition(0);
                                card.setTopCard(true);
                                card.getImageView().setOnDragListener(null);
                                card.getImageView().setOnDragListener(new View.OnDragListener() {
                                    @Override
                                    public boolean onDrag(View v, DragEvent event) {
                                        return onDragCard(v, event);
                                    }
                                });
                                gameFieldLayout.addView(card.getImageView());
                                card.getImageView().setTag(card);
                                updateCounter();
                                Log.d(TAG, "Успешно положили карту в дом");
                            }
                            else Log.d(TAG, "Неподходящее место для карты");
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            i++; // для установки картинки из массива картинок
            k = k + INDENT_HOUSES_Y; //для сдвига этих домов по у
        }
    }

    public void setCardImageView(ImageView imageView, float x, float y) {
        imageView.setX(x);
        imageView.setY(y);
        imageView.setLayoutParams(layoutParams);
    }

    public void makeTopCard(int bucket, int position) {
        for (int i = 0; i < COUNT_OF_CARDS; i++) {
            if ((deck[i].getBucket() == bucket) && (deck[i].getPosition() == position)) {
                if (!deck[i].isFaceUp()) flip(deck[i], true);
                deck[i].setTopCard(true);
            }
        }
    }

    public boolean onDragCard(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                Card card1 = (Card) view.getTag(); // карта, которая переносится
                Card card2 = (Card) v.getTag(); // карта, которая принимает
                if ((card1.getValue() + 1 == card2.getValue()) && // для перетаскивания
                        ((card1.getSuit().isBlack() && card2.getSuit().isRed()) || (card1.getSuit().isRed() && card2.getSuit().isBlack())) &&
                        (card2.getBucket() != DIAMOND_HOUSE || card2.getBucket() != CLUB_HOUSE || card2.getBucket() != HEART_HOUSE || card2.getBucket() != SPADE_HOUSE)) {
                    gameFieldLayout.removeView(card1.getImageView());
                    setCardImageView(card1.getImageView(), card2.getImageView().getX(), card2.getImageView().getY() + conventUnitY);
                    card2.setTopCard(false);
                    makeTopCard(card1.getBucket(), card1.getPosition() - 1); // сделать верхней ту карту, которая была под переносимой
                    if (card1.isTopCard() || card1.getBucket() == LEAD_DECK_OPEN_BUCKET) { // если карта 1
                        card1.setBucket(card2.getBucket());
                        card1.setPosition(card2.getPosition() + 1);
                        gameFieldLayout.addView(card1.getImageView());
                        card1.getImageView().setTag(card1);
                        Log.d(TAG, "Успешно положили карту: " + (card1.getValue() + 1) + " : " + card1.getSuit() + " : " + card1.getBucket()
                                + " : " + card1.getPosition() + " : " + card1.isFaceUp() + " : " + card1.getImageView().getId() + " : " + card1.isTopCard());
                    } else { // иначе, если пачка
                        transferPack(card1, card2.getBucket(), card2.getPosition() + 1);
                        Log.d(TAG, "Успешно положили пачку");
                    }
                    updateCounter();
                } else if ((card1.getValue() == card2.getValue() + 1) && // для расположения в домах
                        (card1.getSuit() == card2.getSuit()) &&
                        (card2.getBucket() == DIAMOND_HOUSE || card2.getBucket() == CLUB_HOUSE || card2.getBucket() == HEART_HOUSE || card2.getBucket() == SPADE_HOUSE)) {
                    gameFieldLayout.removeView(card1.getImageView());
                    setCardImageView(card1.getImageView(), card2.getImageView().getX(), card2.getImageView().getY());
                    makeTopCard(card1.getBucket(), card1.getPosition() - 1);
                    card1.setBucket(card2.getBucket());
                    card1.setPosition(card2.getPosition() + 1);
                    card1.setTopCard(true);
                    gameFieldLayout.addView(card1.getImageView());
                    card1.getImageView().setTag(card1);
                    updateCounter();
                    Log.d(TAG, "Успешно положили карту в дом");
                    isGameOver();
                }
                else Log.d(TAG, "Неподходящее место для карты");
                break;
            default:
                break;
        }
        return true;
    }

    public void transferPack(Card card, int bucket, int position) {
        int lastBucket = card.getBucket();
        int lastPos = card.getPosition() + 1;

        card.setBucket(bucket);
        card.setPosition(position);

        int newPos = card.getPosition();

        gameFieldLayout.addView(card.getImageView());
        card.getImageView().setTag(card);

        int n = 1;
        for (int i = 0; i < COUNT_OF_CARDS; i++) {// проходим по всем картам, которые лежат на короле, чтобы перенести их на новые места
            if (deck[i].getBucket() == lastBucket && deck[i].getPosition() == lastPos) { // если есть карта в старом бакете на позиции выше
                gameFieldLayout.removeView(deck[i].getImageView());
                setCardImageView(deck[i].getImageView(), card.getImageView().getX(), card.getImageView().getY() + conventUnitY * n); // кладем её на координаты короля со сдвигом по у
                deck[i].setBucket(card.getBucket());
                newPos++;
                deck[i].setPosition(newPos);
                gameFieldLayout.addView(deck[i].getImageView());
                deck[i].getImageView().setTag(deck[i]);
                n++;
                lastPos++;
                i = 0; // сбрасываем счетчик, потому что карты с нужными позициями могут быть расположены в массиве не по порядку
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void flip(final Card card, boolean side) {
        card.setFaceUp(side);
        if (side) {
            card.setTopCard(true);
            card.getImageView().setImageBitmap(decodeSampledBitmapFromResource(getResources(), card.getImage(), SIZE_IMG, SIZE_IMG));
            card.getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onTouchCard(v, event, card);
                }
            });
            card.getImageView().setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    return onDragCard(v, event);
                }
            });
        } else {
            card.setTopCard(false);
            card.getImageView().setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[COUNT_OF_CARDS], SIZE_IMG, SIZE_IMG));
            card.getImageView().setOnDragListener(null);
            card.getImageView().setOnTouchListener(null);
            card.getImageView().setTag(card);
        }
    }

    public int numberOfCardsInBucket(int bucket) {
        int number = 0;
        for (int i = 0; i < COUNT_OF_CARDS; i++) {
            if (deck[i].getBucket() == bucket)
                number++;
        }
        return number;
    }

    public void isGameOver() {
        int gameOverBuckets = 0;
        for (int i = DIAMOND_HOUSE; i <= SPADE_HOUSE; i++) {
            int numberOfCards = numberOfCardsInBucket(i);
            if (numberOfCards == COUNT_OF_VALUES) gameOverBuckets++;
            Log.d(TAG, "Количество карт в " + i + " доме: " + numberOfCards);
        }
        if (gameOverBuckets == COUNT_OF_SUITS) {
            Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
            mChronometer.stop();
            long endTime = System.nanoTime();
            long elapsedMilliSeconds = endTime - startTime;
            intent.putExtra("elapsedMilliSeconds", elapsedMilliSeconds);
            intent.putExtra("counter", counter + "");
            startActivity(intent);
            this.finish();
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateCounter() {
        counter++;
        counterTxt.setText(counter + "");
    }

    @SuppressLint({"NewApi", "LocalSuppress"})
    public void generateId(ImageView imgView, int index) {
        int id = View.generateViewId();
        imgView.setId(id);
        imgViews[index] = id;
    }

    // Следующие 2 метода необходимы для сжатия изображения по качеству для экономии памяти
    // Взяты с сайта с документацией https://developer.android.com/topic/performance/graphics/load-bitmap#java

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
    // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
