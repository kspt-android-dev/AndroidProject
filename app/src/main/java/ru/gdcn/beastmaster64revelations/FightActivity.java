package ru.gdcn.beastmaster64revelations;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters.DummyEnemy;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.UIElements.CharacterCard;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FightActivity extends AppCompatActivity {

    List<Button> allActionButtons = new ArrayList<>();

    Integer currentZ = 10;
    CountDownTimer AItimer;

    TextView fightConsoleText;
    NestedScrollView fightConsoleScroll;

    CharacterCard playerCard;
    CharacterCard enemyCard;

    FrameLayout cardHolderPlayer;
    FrameLayout cardHolderEnemy;

    Character player;
    NPC enemy;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fight);

        player = (PlayerClass) getIntent().getSerializableExtra("player");
        enemy = (DummyEnemy) getIntent().getSerializableExtra("enemy");

        playerCard = new CharacterCard(this, null, player);
        enemyCard = new CharacterCard(this, null, enemy);

        cardHolderPlayer = findViewById(R.id.activity_fight_placeholder_left);
        cardHolderEnemy = findViewById(R.id.activity_fight_placeholder_right);

        cardHolderPlayer.addView(playerCard);
        cardHolderEnemy.addView(enemyCard);

        progressBar = findViewById(R.id.activity_fight_progressBar);
        fightConsoleText = findViewById(R.id.activity_fight_console);
        fightConsoleScroll = findViewById(R.id.activity_fight_consoleScroll);

        fightConsoleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fightConsoleScroll.post(() -> fightConsoleScroll.fullScroll(ScrollView.FOCUS_DOWN));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button kickButton = findViewById(R.id.activity_fight_action_kick);
        Button healButton = findViewById(R.id.activity_fight_action_heal);
        Button magicButton = findViewById(R.id.activity_fight_action_magic);

        allActionButtons.add(kickButton);
        allActionButtons.add(healButton);
        allActionButtons.add(magicButton);

        kickButton.setOnClickListener(v -> {

            if (player.isDead())
                onPlayerDead();

            playKickAnimation(cardHolderPlayer);
            BasicAttack basicAttack = new BasicAttack("Удар", 1.0);
            basicAttack.use(player, enemy);
            playerCard.updateContent();
            enemyCard.updateContent();
            actionDelayOneSecond();
            logFightAction(player.getName() + " ударил " + enemy.getName() + "!");
        });

        healButton.setOnClickListener(v -> {

            if (player.isDead())
                onPlayerDead();

            playBounceAnimation(cardHolderPlayer);

            player.dealHeal(5);
            playerCard.updateContent();
            enemyCard.updateContent();
            actionDelayOneSecond();
            logFightAction(player.getName() + " только что вылечился!");
        });

        runEnemyAI(1500);
    }

    private void runEnemyAI(int millisDelay) {

        AItimer = new CountDownTimer(millisDelay, 25) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (player.isDead()) {
                    onPlayerDead();
                    return;
                }
                if (enemy.isDead()){
                    onEnemyDead();
                    return;
                }
                runOnUiThread(() -> {
                    Action enemyAction = enemy.makeNextFightTurn(player);
                    logFightAction(enemy.getName() + " использует " + enemyAction.getName() + "!!!");
                    enemyAction.use(enemy, player);
                    playerCard.updateContent();
                    enemyCard.updateContent();
                });
                runEnemyAI(1500);
            }
        };

        AItimer.start();
    }


    private void logFightAction(String text){
        fightConsoleText.append("\n" + text);
    }

    private void disableAllButtons(){
        for (Button button: allActionButtons){
            if (button != null)
                button.setEnabled(false);
        }
    }

    private void enableAllButtons(){
        for (Button button: allActionButtons){
            if (button != null)
                button.setEnabled(true);
        }
    }

    private void actionDelayOneSecond(){
        disableAllButtons();
        playerCard.updateContent();
        enemyCard.updateContent();
        CountDownTimer buttonTimer = new CountDownTimer(1000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(() -> progressBar.setProgress((int) (millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                runOnUiThread(() -> enableAllButtons());
                runOnUiThread(() -> progressBar.setProgress(0));
            }
        };
        buttonTimer.start();
    }

    private void playKickAnimation(FrameLayout layout){
        Animation kickAnim = AnimationUtils.loadAnimation(this, R.anim.face_kick_anim);
        kickAnim.setRepeatCount(1);
        layout.setZ(currentZ++);
        layout.startAnimation(kickAnim);
    }

    private void playBounceAnimation(FrameLayout layout){
        Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.face_bounce_anim);
        bounceAnim.setRepeatCount(1);
        layout.setZ(currentZ++);
        layout.startAnimation(bounceAnim);
    }

    private void onPlayerDead() {
//        AlertDialog dialog = new AlertDialog(this);
//        .setMessage("Вы проиграли!")
//                .setPositiveButton("Выйти!", (dialog, which) -> super.onBackPressed()).show();
    }

    private void onEnemyDead() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Вы выиграли сражение!!!")
//                .setPositiveButton("Круто!", (dialog, which) -> super.onBackPressed()).show();
    }

}
