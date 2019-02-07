package app.galentin.ru.nonograms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static java.lang.Integer.parseInt;

class FileParser {

    private String answer;
    private int columns;
    private String color1;
    private String color2;
    private String result;

    Cell[] init(String nameFile) {
        Cell[] masCell = null;
        try {
            InputStream raw = getClass().getResourceAsStream(nameFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(raw, StandardCharsets.UTF_8));
            color1 = reader.readLine();
            color2 = reader.readLine();
            int lines = parseInt(reader.readLine());
            columns = parseInt(reader.readLine());
            int linesField = parseInt(reader.readLine());
            int columnsField = parseInt(reader.readLine());
            String[] positionNumColor1 = reader.readLine().split(",");
            String[] numColor1 = reader.readLine().split(",");
            String[] positionNumColor2 = reader.readLine().split(",");
            String[] numColor2 = reader.readLine().split(",");
            answer = reader.readLine();
            result = reader.readLine();
            reader.close();
            masCell = new Cell[lines * columns];
            for (int j = 0; j < lines * columns; j++) {
                masCell[j] = new Cell();
            }
            for (int j = 0; j < lines * columns; j++) {
                if ((j < columns * (lines - linesField)) || ((j > columns * (j / columns) - 1) && (j < columns * (j / columns) + columns - columnsField))) {
                    masCell[j].setColor("#FFF8DC");
                    masCell[j].setState(Cell.State.Boundaries);
                    if ((j < columns * (lines - linesField)) && ((j > columns * (j / columns) - 1) && (j < columns * (j / columns) + columns - columnsField))) {
                        masCell[j].setColor(color1);
                        masCell[j].setState(Cell.State.ActiveColor);
                    }
                }

            }
            for (int j = 0; j < positionNumColor1.length; j++) {
                masCell[parseInt(positionNumColor1[j])].setNum(numColor1[j]);
                masCell[parseInt(positionNumColor1[j])].setColor(color1);
                masCell[parseInt(positionNumColor1[j])].setState(Cell.State.NumColor);
            }
            for (int j = 0; j < positionNumColor2.length; j++) {
                masCell[parseInt(positionNumColor2[j])].setNum(numColor2[j]);
                masCell[parseInt(positionNumColor2[j])].setColor(color2);
                masCell[parseInt(positionNumColor2[j])].setState(Cell.State.NumColor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return masCell;
    }

    int getColumns() {
        return columns;
    }

    String getAnswer() {
        return answer;
    }

    String getColor1() {
        return color1;
    }

    String getColor2() {
        return color2;
    }

    String getResult() {
        return result;
    }
}
