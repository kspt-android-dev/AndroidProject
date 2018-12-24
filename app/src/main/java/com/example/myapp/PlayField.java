package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

// https://online-fotoshop.ru/ubrat-fon-online/

@SuppressLint("ViewConstructor")
public class PlayField extends View {

    public int getFlagAboutGrid() {
        return flagAboutGrid;
    }

    private final int flagAboutGrid; // 3 - 3x3, 5 - 5x5

    private TextView cX;
    private TextView cO;

    private final Drawable imageKrestik = getResources().getDrawable(R.drawable.xxx);
    private final Drawable imageNolik = getResources().getDrawable(R.drawable.nullik);


    private final Logic logic = new Logic();


    private final Context context;
    private final Paint paint = new Paint();

    public PlayField(Context context, int flagAboutGrid) {
        super(context);
        this.context = context;
        this.flagAboutGrid = flagAboutGrid;


        if (flagAboutGrid == 3) {
            cX = ((Main2Activity) context).findViewById(R.id.countWinX);
            cO = ((Main2Activity) context).findViewById(R.id.countWinO);
        } else if (flagAboutGrid == 4) {
            cX = ((Main2Activity_bot) context).findViewById(R.id.countWinX);
            cO = ((Main2Activity_bot) context).findViewById(R.id.countWinO);
        } else if (flagAboutGrid == 5) {
            cX = ((Main3Activity) context).findViewById(R.id.countWinX);
            cO = ((Main3Activity) context).findViewById(R.id.countWinO);
        } else if (flagAboutGrid == 6) {
            cX = ((Main3Activity_bot) context).findViewById(R.id.countWinX);
            cO = ((Main3Activity_bot) context).findViewById(R.id.countWinO);
        }

        this.setClickable(true);

        this.setOnTouchListener(new PlayFieldListener(this));

        startGame();
    }


    private void startGame() {
        invalidate();
    }

    public void restartGame() {
        Logic.cells.clear();
        Logic.flagTeamWin = 0;
        invalidate();
    }

    public void exitGame() {
        Logic.cells.clear();
        Logic.winX = 0;
        Logic.winO = 0;
        Logic.flagTeamWin = 0;
        Logic.countStep = 0;

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (Logic.flagTeamWin != 0) {
            this.setClickable(false); // если победа на доске нарисована
        }
        /* build x|o */
        if (this.flagAboutGrid == 3 || this.flagAboutGrid == 4) {

            for (int i = 0; i != Logic.cells.size(); i++) {

                if (Logic.cells.get(i).getKek() == 0) {

                    imageKrestik.setBounds(Logic.cells.get(i).getX() - 85, Logic.cells.get(i).getY() - 85, Logic.cells.get(i).getX() + 85, Logic.cells.get(i).getY() + 85);
                    imageKrestik.draw(canvas);

                } else {

                    imageNolik.setBounds(Logic.cells.get(i).getX() - 85, Logic.cells.get(i).getY() - 85, Logic.cells.get(i).getX() + 85, Logic.cells.get(i).getY() + 85);
                    imageNolik.draw(canvas);

                }
            }
        }

        if (this.flagAboutGrid == 5 || this.flagAboutGrid == 6) {
            for (int i = 0; i != Logic.cells.size(); i++) {

                if (Logic.cells.get(i).getKek() == 0) {

                    imageKrestik.setBounds(Logic.cells.get(i).getX() - 75, Logic.cells.get(i).getY() - 75, Logic.cells.get(i).getX() + 75, Logic.cells.get(i).getY() + 75);
                    imageKrestik.draw(canvas);

                } else {

                    imageNolik.setBounds(Logic.cells.get(i).getX() - 62, Logic.cells.get(i).getY() - 62, Logic.cells.get(i).getX() + 62, Logic.cells.get(i).getY() + 62);
                    imageNolik.draw(canvas);

                }

            }
        }


        Logic.countStep++;


        if ((this.flagAboutGrid == 3 || this.flagAboutGrid == 4) && logic.checkWin3x3()) {

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(20);

            switch (logic.arrWin[0]) {
                case 1:
                    canvas.drawLine(163, 163, 817, 163, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 2:
                    canvas.drawLine(163, 523, 817, 523, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 3:
                    canvas.drawLine(163, 861, 817, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 4:
                    canvas.drawLine(163, 163, 163, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 5:
                    canvas.drawLine(490, 163, 490, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 6:
                    canvas.drawLine(817, 163, 817, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 7:
                    canvas.drawLine(163, 163, 817, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 8:
                    canvas.drawLine(817, 163, 163, 861, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;


                case 11:
                    canvas.drawLine(163, 163, 817, 163, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 12:
                    canvas.drawLine(163, 523, 817, 523, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 13:
                    canvas.drawLine(163, 861, 817, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 14:
                    canvas.drawLine(163, 163, 163, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 15:
                    canvas.drawLine(490, 163, 490, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 16:
                    canvas.drawLine(817, 163, 817, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 17:
                    canvas.drawLine(163, 163, 817, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 18:
                    canvas.drawLine(817, 163, 163, 861, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
            }
        }


        if ((this.flagAboutGrid == 5 || this.flagAboutGrid == 6) && logic.checkWin5x5()) {

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(15);

            switch (logic.arrWin[0]) {
                case 1:
                    canvas.drawLine(102, 104, 886, 104, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 2:
                    canvas.drawLine(102, 312, 886, 312, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 3:
                    canvas.drawLine(102, 525, 886, 525, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 31:
                    canvas.drawLine(102, 735, 886, 735, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 32:
                    canvas.drawLine(102, 946, 886, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 4:
                    canvas.drawLine(102, 104, 102, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 5:
                    canvas.drawLine(297, 104, 297, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 6:
                    canvas.drawLine(496, 104, 496, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 61:
                    canvas.drawLine(695, 104, 695, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 62:
                    canvas.drawLine(886, 104, 886, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 7:
                    canvas.drawLine(102, 104, 886, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;
                case 8:
                    canvas.drawLine(886, 104, 102, 946, paint);
                    Logic.winO++;
                    cO.setText(String.valueOf(Logic.winO));
                    break;


                case 11:
                    canvas.drawLine(102, 104, 886, 104, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 12:
                    canvas.drawLine(102, 312, 886, 312, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 13:
                    canvas.drawLine(102, 525, 886, 525, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 131:
                    canvas.drawLine(102, 735, 886, 735, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 132:
                    canvas.drawLine(102, 946, 886, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 14:
                    canvas.drawLine(102, 104, 102, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 15:
                    canvas.drawLine(297, 104, 297, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 16:
                    canvas.drawLine(496, 104, 496, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 161:
                    canvas.drawLine(695, 104, 695, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 162:
                    canvas.drawLine(886, 104, 886, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 17:
                    canvas.drawLine(102, 104, 886, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
                case 18:
                    canvas.drawLine(886, 104, 102, 946, paint);
                    Logic.winX++;
                    cX.setText(String.valueOf(Logic.winX));
                    break;
            }
        }

    }


}

