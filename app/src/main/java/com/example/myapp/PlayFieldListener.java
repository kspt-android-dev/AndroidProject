package com.example.myapp;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

class PlayFieldListener implements View.OnTouchListener {

    private float startX = 0;
    private float startY = 0;

    private final PlayField playField;
    private final Logic logic = new Logic();
    private final bot bot = new bot();

    PlayFieldListener(PlayField playField) {
        this.playField = playField;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        if (playField.getFlagAboutGrid() == 3) {

            Cell checkPoint = reCoordinate3x3(new Cell((int) motionEvent.getX(), (int) motionEvent.getY(), -1)); // -1 потому что не используется


            if (checkPoint != null && logic.isEmptyOnCell(checkPoint)) { // null - нажатие на ребро

                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:

                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;

                    case ACTION_UP:

                        Cell point;
                        if (Logic.countStep % 2 == 0)
                            point = reCoordinate3x3(new Cell((int) startX, (int) startY, 1));
                        else point = reCoordinate3x3(new Cell((int) startX, (int) startY, 0));

                        if (point != null) {
                            logic.setFieldCell(point);
                            playField.invalidate();
                            if (logic.checkWin3x3()) playField.setClickable(false);
                        }
                        break;
                }
            }
        } else if (playField.getFlagAboutGrid() == 4) { // bot (3x3)

            Cell checkPoint = reCoordinate3x3(new Cell((int) motionEvent.getX(), (int) motionEvent.getY(), -1)); // -1 not usable


            if (checkPoint != null && logic.isEmptyOnCell(checkPoint)) {// null - нажатие на ребро

                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:

                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;

                    case ACTION_UP:

                        Cell point;
                        if (Logic.countStep % 2 == 0)
                            point = reCoordinate3x3(new Cell((int) startX, (int) startY, 1));
                        else point = reCoordinate3x3(new Cell((int) startX, (int) startY, 0));

                        if (point != null) {
                            logic.setFieldCell(point);
                            playField.invalidate();
                            if (logic.checkWin3x3()) playField.setClickable(false);
                            else bot.goBy3x3();
                            if (logic.checkWin3x3()) playField.setClickable(false);
                        }
                        break;
                }
            }
        } else if (playField.getFlagAboutGrid() == 5) {

            Cell checkPoint = reCoordinate5x5(new Cell((int) motionEvent.getX(), (int) motionEvent.getY(), -1));    // last parameter not usable


            if (checkPoint != null && logic.isEmptyOnCell(checkPoint)) {// null - нажатие на ребро

                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:

                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;

                    case ACTION_UP:
                        Cell point;
                        if (Logic.countStep % 2 == 0)
                            point = reCoordinate5x5(new Cell((int) startX, (int) startY, 1));
                        else point = reCoordinate5x5(new Cell((int) startX, (int) startY, 0));

                        if (point != null) {
                            logic.setFieldCell(point);
                            playField.invalidate();
                            if (logic.checkWin5x5()) playField.setClickable(false);
                        }
                        break;
                }
            }
        } else if (playField.getFlagAboutGrid() == 6) {

            Cell checkPoint = reCoordinate5x5(new Cell((int) motionEvent.getX(), (int) motionEvent.getY(), -1)); // last param no use


            if (checkPoint != null && logic.isEmptyOnCell(checkPoint)) {// null - нажатие на ребро

                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:

                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;

                    case ACTION_UP:

                        Cell point;
                        if (Logic.countStep % 2 == 0)
                            point = reCoordinate5x5(new Cell((int) startX, (int) startY, 1));
                        else point = reCoordinate5x5(new Cell((int) startX, (int) startY, 0));

                        if (point != null) {
                            logic.setFieldCell(point);
                            playField.invalidate();
                            if (logic.checkWin5x5()) playField.setClickable(false);
                            else bot.goBy5x5();
                            if (logic.checkWin5x5()) playField.setClickable(false);
                        }
                        break;
                }
            }
        }
        return false;
    }


    private Cell reCoordinate3x3(Cell point) {
        if (point.getX() > 0 && point.getX() < 327 && point.getY() > 0 && point.getY() < 349) {
            point.setX(163);
            point.setY(163);
            return point;
        }
        if (point.getX() > 328 && point.getX() < 654 && point.getY() > 0 && point.getY() < 349) {
            point.setX(490);
            point.setY(163);
            return point;
        }
        if (point.getX() > 655 && point.getX() < 982 && point.getY() > 0 && point.getY() < 349) {
            point.setX(817);
            point.setY(163);
            return point;
        }
        if (point.getX() > 0 && point.getX() < 327 && point.getY() > 350 && point.getY() < 700) {
            point.setX(163);
            point.setY(523);
            return point;
        }
        if (point.getX() > 328 && point.getX() < 654 && point.getY() > 350 && point.getY() < 700) {
            point.setX(490);
            point.setY(523);
            return point;
        }
        if (point.getX() > 655 && point.getX() < 982 && point.getY() > 350 && point.getY() < 700) {
            point.setX(817);
            point.setY(523);
            return point;
        }
        if (point.getX() > 0 && point.getX() < 327 && point.getY() > 701 && point.getY() < 1048) {
            point.setX(163);
            point.setY(861);
            return point;
        }
        if (point.getX() > 328 && point.getX() < 654 && point.getY() > 701 && point.getY() < 1048) {
            point.setX(490);
            point.setY(861);
            return point;
        }
        if (point.getX() > 655 && point.getX() < 982 && point.getY() > 701 && point.getY() < 1048) {
            point.setX(817);
            point.setY(861);
            return point;
        } else return null;
    }


    private Cell reCoordinate5x5(Cell point) {
        if (point.getX() > 13 && point.getX() < 186 && point.getY() > 12 && point.getY() < 196) {
            point.setX(102);
            point.setY(104);
            return point;
        }
        if (point.getX() > 209 && point.getX() < 382 && point.getY() > 12 && point.getY() < 196) {
            point.setX(297);
            point.setY(104);
            return point;
        }
        if (point.getX() > 406 && point.getX() < 582 && point.getY() > 12 && point.getY() < 196) {
            point.setX(496);
            point.setY(104);
            return point;
        }
        if (point.getX() > 603 && point.getX() < 783 && point.getY() > 12 && point.getY() < 196) {
            point.setX(695);
            point.setY(104);
            return point;
        }
        if (point.getX() > 802 && point.getX() < 966 && point.getY() > 12 && point.getY() < 196) {
            point.setX(886);
            point.setY(104);
            return point;
        }

        if (point.getX() > 13 && point.getX() < 186 && point.getY() > 220 && point.getY() < 411) {
            point.setX(102);
            point.setY(312);
            return point;
        }
        if (point.getX() > 209 && point.getX() < 382 && point.getY() > 220 && point.getY() < 411) {
            point.setX(297);
            point.setY(312);
            return point;
        }
        if (point.getX() > 406 && point.getX() < 582 && point.getY() > 220 && point.getY() < 411) {
            point.setX(496);
            point.setY(312);
            return point;
        }
        if (point.getX() > 603 && point.getX() < 783 && point.getY() > 220 && point.getY() < 411) {
            point.setX(695);
            point.setY(312);
            return point;
        }
        if (point.getX() > 802 && point.getX() < 966 && point.getY() > 220 && point.getY() < 411) {
            point.setX(886);
            point.setY(312);
            return point;
        }

        if (point.getX() > 13 && point.getX() < 186 && point.getY() > 434 && point.getY() < 622) {
            point.setX(102);
            point.setY(525);
            return point;
        }
        if (point.getX() > 209 && point.getX() < 382 && point.getY() > 434 && point.getY() < 622) {
            point.setX(297);
            point.setY(525);
            return point;
        }
        if (point.getX() > 406 && point.getX() < 582 && point.getY() > 434 && point.getY() < 622) {
            point.setX(496);
            point.setY(525);
            return point;
        }
        if (point.getX() > 603 && point.getX() < 783 && point.getY() > 434 && point.getY() < 622) {
            point.setX(695);
            point.setY(525);
            return point;
        }
        if (point.getX() > 802 && point.getX() < 966 && point.getY() > 434 && point.getY() < 622) {
            point.setX(886);
            point.setY(525);
            return point;
        }

        if (point.getX() > 13 && point.getX() < 186 && point.getY() > 645 && point.getY() < 833) {
            point.setX(102);
            point.setY(735);
            return point;
        }
        if (point.getX() > 209 && point.getX() < 382 && point.getY() > 645 && point.getY() < 833) {
            point.setX(297);
            point.setY(735);
            return point;
        }
        if (point.getX() > 406 && point.getX() < 582 && point.getY() > 645 && point.getY() < 833) {
            point.setX(496);
            point.setY(735);
            return point;
        }
        if (point.getX() > 603 && point.getX() < 783 && point.getY() > 645 && point.getY() < 833) {
            point.setX(695);
            point.setY(735);
            return point;
        }
        if (point.getX() > 802 && point.getX() < 966 && point.getY() > 645 && point.getY() < 833) {
            point.setX(886);
            point.setY(735);
            return point;
        }

        if (point.getX() > 13 && point.getX() < 186 && point.getY() > 856 && point.getY() < 1041) {
            point.setX(102);
            point.setY(946);
            return point;
        }
        if (point.getX() > 209 && point.getX() < 382 && point.getY() > 856 && point.getY() < 1041) {
            point.setX(297);
            point.setY(946);
            return point;
        }
        if (point.getX() > 406 && point.getX() < 582 && point.getY() > 856 && point.getY() < 1041) {
            point.setX(496);
            point.setY(946);
            return point;
        }
        if (point.getX() > 603 && point.getX() < 783 && point.getY() > 856 && point.getY() < 1041) {
            point.setX(695);
            point.setY(946);
            return point;
        }
        if (point.getX() > 802 && point.getX() < 966 && point.getY() > 856 && point.getY() < 1041) {
            point.setX(886);
            point.setY(946);
            return point;
        } else return null;
    }

}
