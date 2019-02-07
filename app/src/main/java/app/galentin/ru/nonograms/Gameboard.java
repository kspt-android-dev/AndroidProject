package app.galentin.ru.nonograms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class Gameboard extends AppCompatActivity {
    String colorButtonActive;
    String textButtonActive;
    Cell masCell[];
    FieldAdapter fieldAdapter;
    GridView gridView;
    StringBuilder result;
    FileParser parser = new FileParser();
    private static final String TAG = "My_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        final Button buttonColor1 = (Button) findViewById(R.id.color1);
        final Button buttonColor2 = (Button) findViewById(R.id.color2);
        final Button blockCell = (Button) findViewById(R.id.blockCell);
        gridView = (GridView) findViewById(R.id.gridV);
        final Intent intent = getIntent();
        masCell = parser.init(intent.getStringExtra("fileName"));
        buttonColor1.setBackgroundColor(Color.parseColor(parser.getColor1()));
        buttonColor2.setBackgroundColor(Color.parseColor(parser.getColor2()));
        fieldAdapter = new FieldAdapter(Gameboard.this, masCell);
        gridView.setNumColumns(parser.getColumns());
        gridView.setAdapter(fieldAdapter);
        colorButtonActive = parser.getColor1();
        result = new StringBuilder(parser.getResult());
        textButtonActive = " ";
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fieldAdapter.cells[position].TapCell(colorButtonActive, textButtonActive))  Log.d(TAG, "Cell change");
                else Log.d(TAG, "Invalid cell pressed");
                fieldAdapter.notifyDataSetChanged();
                if(fieldAdapter.cells[position].getColor().equals(parser.getColor1())) result.setCharAt(position,'.');
                else if(fieldAdapter.cells[position].getColor().equals(parser.getColor2())) result.setCharAt(position,'-');
                else result.setCharAt(position,'X');
                if(parser.getAnswer().equals(result.toString())) {
                    try{
                        Intent intent = new Intent(Gameboard.this, Gameover.class);
                        startActivity(intent);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.color1:
                        colorButtonActive = parser.getColor1();
                        textButtonActive = buttonColor1.getText().toString();
                        break;
                    case R.id.color2:
                        colorButtonActive = parser.getColor2();
                        textButtonActive = buttonColor2.getText().toString();
                        break;
                    case R.id.blockCell:
                        colorButtonActive = "#ffffff";
                        textButtonActive = blockCell.getText().toString();
                        break;
                }
                for (Cell cell : masCell){
                    if(cell.getState() == Cell.State.ActiveColor) cell.setColor(colorButtonActive);
                    Log.d(TAG, "Change active color");
                }
                fieldAdapter.notifyDataSetChanged();
            }
        };
        buttonColor1.setOnClickListener(onClickListener);
        buttonColor2.setOnClickListener(onClickListener);
        blockCell.setOnClickListener(onClickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("colorButtonActive", colorButtonActive);
        outState.putSerializable("masCell", masCell);
        outState.putString("textButtonActive", textButtonActive);
        outState.putString("result", result.toString());
        Log.d(TAG, "Save instance state");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        colorButtonActive = savedInstanceState.getString("colorButtonActive");
        textButtonActive = savedInstanceState.getString("textButtonActive");
        result = new StringBuilder(savedInstanceState.getString("result"));
        masCell = (Cell[]) savedInstanceState.getSerializable("masCell");
        fieldAdapter.cells = (Cell[]) savedInstanceState.getSerializable("masCell");
        for (Cell cell : masCell){
            if(cell.getState() == Cell.State.ActiveColor) cell.setColor(colorButtonActive);
        }
        fieldAdapter.notifyDataSetChanged();
        Log.d(TAG, "Restore saved instance of gameboard");
    }
}
