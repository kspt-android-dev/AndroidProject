package ru.gdcn.beastmaster64revelations.GameActivities;

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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicHeal;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters.DummyEnemy;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Fight.FightResult;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.UIElements.CharacterCard;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FightActivity extends AppCompatActivity {

    //Все кнопочки, которые будут отключаться во время отката действий
    List<Button> allActionButtons = new ArrayList<>();

    //фончик
    ImageView backImage;

    //Костыль, чтобы атакующий персонаж всегда был поверх противника
    //(Было придумано за 0.1 сек и осталось до сих пор)
    Integer currentZ = 10;

    //таймеры для действий игрока и AI
    CountDownTimer AItimer;
    CountDownTimer playerTimer;

    //текст и скролл консольки
    TextView fightConsoleText;
    NestedScrollView fightConsoleScroll;

    //Кастомные вьюшки для отображения карточек игрока и врага
    CharacterCard playerCard;
    CharacterCard enemyCard;

    //Холдеры для карточек
    FrameLayout cardHolderPlayer;
    FrameLayout cardHolderEnemy;

    //Прогрессбары с кулдауном, игрок и враг
    Character player;
    NPC enemy;
    ImageView progressBar;
    ImageView enemyProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        //По умолчанию никто не умер и возврат будет "ничья"
        setResult(FightResult.ALL_ALIVE);

        //Задаём фончик как в прошлом активити
        backImage = new ProportionalImageView(this);
        backImage.setAlpha(0.45f);
        FrameLayout mainFrame = findViewById(R.id.activity_fight_backFrame);
        mainFrame.addView(backImage, 0);

        //Достаём наших персонажей из интента
        player = (PlayerClass) getIntent().getSerializableExtra("player");
        enemy = (DummyEnemy) getIntent().getSerializableExtra("enemy");
        setBackground((LocationType) getIntent().getSerializableExtra("locationType"));

        //Создаём карточки наших персонажей
        playerCard = new CharacterCard(this, null, player);
        enemyCard = new CharacterCard(this, null, enemy);

        //достаём холдеры
        cardHolderPlayer = findViewById(R.id.activity_fight_placeholder_left);
        cardHolderEnemy = findViewById(R.id.activity_fight_placeholder_right);

        //Засовываем в холдеры наши карточки
        cardHolderPlayer.addView(playerCard);
        cardHolderEnemy.addView(enemyCard);

        //Достаём прогрессбары и консольку
        progressBar = findViewById(R.id.activity_fight_progressBarPlayer);
        enemyProgressBar = findViewById(R.id.activity_fight_progressBarEnemy);
        fightConsoleText = findViewById(R.id.activity_fight_console);
        fightConsoleScroll = findViewById(R.id.activity_fight_consoleScroll);

        //Отслеживаем добавление текста в консоль и проматываем наш скролл
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

        //Достаём кнопочки
        Button kickButton = findViewById(R.id.activity_fight_action_kick);
        Button healButton = findViewById(R.id.activity_fight_action_heal);
        Button magicButton = findViewById(R.id.activity_fight_action_magic);
        Button strongButton = findViewById(R.id.activity_fight_action_kick2);

        //Она пока недоступна в игре (не придумали зачем она)
        strongButton.setEnabled(false);

        //Добавляем в отслеживание чтобы отключать их на время кулдауна
        allActionButtons.add(kickButton);
        allActionButtons.add(healButton);
        allActionButtons.add(magicButton);
//        allActionButtons.add(strongButton);

        runEnemyAI(1500);

        //Криво-костыльно задаём на кнопки удары и кулдаун на них
        kickButton.setOnClickListener(v -> {
            //проигрываем анимацию удара
            playKickAnimationPlayer();
            //Создаём атаку
            BasicAttack basicAttack = new BasicAttack(getString(R.string.fight_log_kick), 1.1);
            //Запоминаем HP
            int hpBefore = enemy.getHP();
            //Бабах!
            basicAttack.use(player, enemy);
            //Обновляем инфу на карточках
            playerCard.updateContent();
            enemyCard.updateContent();
            //Задаём откат кнопочкам
            actionDelay(1200);
            //Логируем в нашу консоль чё случилось
            logFightAction(player.getName() + getString(R.string.fight_log_kicked) + enemy.getName() + "!");
            logFightAction(hpBefore - enemy.getHP() + getString(R.string.fight_log_kicks));

            //Проверяем не умер ли кто-нибудь наконец-то
            if (player.isDead()) {
                onPlayerDead();
                return;
            }
            if (enemy.isDead()){
                onEnemyDead();
                return;
            }
        });

        healButton.setOnClickListener(v -> {

            //Аналогично прошлой кнопочке

            playBounceAnimation(cardHolderPlayer);

            player.dealHeal(player.getIntellect() * 4);
            playerCard.updateContent();
            enemyCard.updateContent();
            actionDelay(500 - player.getIntellect());
            logFightAction(player.getName() + getString(R.string.fight_log_just_healed));

            if (player.isDead()) {
                onPlayerDead();
                return;
            }
            if (enemy.isDead()){
                onEnemyDead();
                return;
            }

        });

        magicButton.setOnClickListener(v -> {
            playBounceAnimation(cardHolderPlayer);

            enemy.dealPhysicalDamage(player.getIntellect()*5);

            playerCard.updateContent();
            enemyCard.updateContent();
            actionDelay(2000 - player.getIntellect()*2);

            AItimer.cancel();
            runEnemyAI(1000);

            logFightAction(player.getName() + getString(R.string.fight_log_used_magic));

            if (player.isDead()) {
                onPlayerDead();
                return;
            }
            if (enemy.isDead()){
                onEnemyDead();
                return;
            }
        });

    }

    private void runEnemyAI(int millisDelay) {

        //Ждём заданное время и спрашиваем у противника что он будет делать

        AItimer = new CountDownTimer(millisDelay, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(() -> enemyProgressBar.setScaleX(millisUntilFinished * 1.0f / millisDelay));
            }

            @Override
            public void onFinish() {
                runOnUiThread(() -> enemyProgressBar.setScaleX(0f));
                runOnUiThread(() -> {
                    //Спрашиваем какое действие он совершит сейчас
                    Action enemyAction = enemy.makeNextFightTurn(player);
                    //Костыльно проверяем какую анимацию произвести, хил или атака
                    if (enemyAction instanceof BasicAttack)
                        playKickAnimationEnemy();
                    if (enemyAction instanceof BasicHeal)
                        playBounceAnimation(cardHolderEnemy);
                    //логируем
                    logFightAction(enemy.getName() + getString(R.string.fight_log_use) + enemyAction.getName() + "!!!");
                    //запоминаем хп
                    int hpBefore = player.getHP();
                    //совершаем действие
                    enemyAction.use(enemy, player);
                    //логируем дамаг если противник юзал удар
                    if (enemyAction instanceof BasicAttack)
                        logFightAction(hpBefore - player.getHP() + getString(R.string.fight_log_kicks));
                    playerCard.updateContent();
                    enemyCard.updateContent();
                });
                runEnemyAI(1500);
                if (player.isDead()) {
                    onPlayerDead();
                    return;
                }
                if (enemy.isDead()){
                    onEnemyDead();
                    return;
                }
            }
        };
        //Собсна стартуем таймер
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

    private void actionDelay(int millis){
        disableAllButtons();
        playerCard.updateContent();
        enemyCard.updateContent();
        playerTimer = new CountDownTimer(millis, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(() -> progressBar.setScaleX(millisUntilFinished * 1.0f / millis));
            }

            @Override
            public void onFinish() {
                runOnUiThread(() -> enableAllButtons());
                runOnUiThread(() -> progressBar.setScaleX(1f));
                if (player.isDead()) {
                    onPlayerDead();
                    return;
                }
                if (enemy.isDead()){
                    onEnemyDead();
                    return;
                }
            }
        };
        playerTimer.start();
    }

    private void playKickAnimationPlayer(){
        Animation kickAnim = AnimationUtils.loadAnimation(this, R.anim.face_kick_right_anim);
        kickAnim.setRepeatCount(1);
        cardHolderPlayer.setZ(currentZ++);
        cardHolderPlayer.startAnimation(kickAnim);
    }

    private void playKickAnimationEnemy(){
        Animation kickAnim = AnimationUtils.loadAnimation(this, R.anim.face_kick_left_anim);
        kickAnim.setRepeatCount(1);
        cardHolderEnemy.setZ(currentZ++);
        cardHolderEnemy.startAnimation(kickAnim);
    }

    private void playBounceAnimation(FrameLayout layout){
        Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.face_bounce_anim);
        bounceAnim.setRepeatCount(1);
        layout.setZ(currentZ++);
        layout.startAnimation(bounceAnim);
    }

    private void onPlayerDead() {
        setResult(FightResult.PLAYER_LOST);
        endFight();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.fight_log_you_lose);
        builder.show();
    }

    private void onEnemyDead() {
        setResult(FightResult.PLAYER_WON);
        endFight();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.fight_log_you_win);
        builder.show();
    }

    private void endFight() {
        disableAllButtons();
        AItimer.cancel();
        if (playerTimer != null)
            playerTimer.cancel();
        enemyProgressBar.setScaleX(1f);
        progressBar.setScaleX(1f);
    }

    public void setBackground(LocationType type){
        int image;
        switch (type){
            case ROCK:
                image = R.drawable.location_desert;
                break;
            case FOREST:
                image = R.drawable.location_forest;
                break;
            case FLATLAND:
                image = R.drawable.location_field;
                break;
            default:
                image = R.drawable.location_forest;
                break;
        }
        backImage.setImageDrawable(backImage.getResources().getDrawable(image));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AItimer.cancel();
        if (playerTimer != null)
            playerTimer.cancel();
    }
}
