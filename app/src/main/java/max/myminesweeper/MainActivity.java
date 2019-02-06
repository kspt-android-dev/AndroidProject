package max.myminesweeper;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final int SETTINGS_ACTIVITY_REQUEST_CODE = 1;
    private static final int DEFAULT_FIELD_HEIGHT = 10;
    private static final int DEFAULT_FIELD_WIDTH = 10;
    private static final int DEFAULT_NUMBER_OF_MINES = 30;

    enum ShownDialog {GREETING, DEFEAT, NONE}

    private ShownDialog shownDialog;
    PlayingField playingField;
    private FrameLayout frameLayout;
    private View.OnClickListener buttonsOnClickListener;
    private Intent startServiceIntent;
    private Logic logic;
    private SharedPreferences preferences;
    private MyViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceIntent = new Intent(this, NotificationService.class);
        stopService(startServiceIntent);

        frameLayout = findViewById(R.id.frame_layout);

        setSharedPreferences();

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        setCellSizeInViewModel();

        setLogic();
        setButtonsOnClickListener();
        addOnClickListenerToButtons();
        addPlayingField();

        updateDialog();
    }

    private void setCellSizeInViewModel() {
        viewModel.setCellSide((int) getResources().getDimension(R.dimen.cell_size));
        updateFieldParamsInViewModel();
    }

    private void setLogic() {
        logic = new Logic(this);
        logic.update(viewModel);
    }

    private void setSharedPreferences() {
        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(startServiceIntent);
        updateViewModel();
        Log.i("MainActivity.onDestroy", "Activity destroyed");
    }

    private void updateViewModel() {
        viewModel.setCondition(logic.getCondition());
        viewModel.setTotalOpenedCells(logic.getTotalOpenCells());
        viewModel.setCellsWithMine(logic.getCellsWithMine());
        viewModel.setOpenCells(logic.getOpenCells());
        viewModel.setShownDialog(shownDialog);
        viewModel.setCells(logic.getCells());
    }

    private void addPlayingField() {
        playingField = new PlayingField(this);
        playingField.setLogic(logic);
        frameLayout.addView(playingField);
    }

    private void setButtonsOnClickListener() {
        buttonsOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_settings:
                        Intent openSettings = new Intent(MainActivity.this, Settings.class);
                        startActivityForResult(openSettings, SETTINGS_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.button_reset:
                        logic.newGame(viewModel);
                        playingField.newGame();
                        break;
                }
            }
        };
    }

    private void addOnClickListenerToButtons() {
        findViewById(R.id.button_settings).setOnClickListener(buttonsOnClickListener);
        findViewById(R.id.button_reset).setOnClickListener(buttonsOnClickListener);
    }

    private void updateDialog() {
        shownDialog = viewModel.getShownDialog();
        if (shownDialog.equals(ShownDialog.DEFEAT)) showDefeatDialog();
        else if (shownDialog.equals(ShownDialog.GREETING)) showGreetingDialog();
    }

    public void showDefeatDialog() {
        shownDialog = ShownDialog.DEFEAT;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.you_lose)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        logic.newGame(viewModel);
                        playingField.newGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        logic.setConditionOverview();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showGreetingDialog() {
        shownDialog = ShownDialog.GREETING;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.you_won)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        logic.newGame(viewModel);
                        playingField.newGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        logic.setConditionOverview();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SETTINGS_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Log.i("Setting result code", "OK");
                    updateFieldParamsInViewModel();
                    frameLayout.removeAllViews();
                    playingField = new PlayingField(this);
                    playingField.setLogic(logic);
                    frameLayout.addView(playingField);
                    logic.newGame(viewModel);
                    playingField.newGame();
                }
                break;
        }
    }

    private void updateFieldParamsInViewModel() {
        viewModel.setFieldHeight((preferences == null) ? DEFAULT_FIELD_HEIGHT :
                Integer.parseInt(preferences.getString("field_height", "10")));
        viewModel.setFieldWidth((preferences == null) ? DEFAULT_FIELD_WIDTH :
                Integer.parseInt(preferences.getString("field_width", "10")));
        viewModel.setNumberOfMines((preferences == null) ? DEFAULT_NUMBER_OF_MINES :
                Integer.parseInt(preferences.getString("number_of_mines", "30")));
    }
}
