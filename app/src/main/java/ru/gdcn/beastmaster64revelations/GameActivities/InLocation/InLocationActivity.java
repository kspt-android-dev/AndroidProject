package ru.gdcn.beastmaster64revelations.GameActivities.InLocation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import ru.gdcn.beastmaster64revelations.GameActivities.FightActivity;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleLocationClass;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleWorldClass;
import ru.gdcn.beastmaster64revelations.GameFragments.InLocationFragment;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Fight.FightResult;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;
import ru.gdcn.beastmaster64revelations.GameActivities.MainMenu.MainMenuActivity;
import ru.gdcn.beastmaster64revelations.GameFragments.MapFragment.MapFragment;
import ru.gdcn.beastmaster64revelations.GameLogger;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.GameFragments.StatsFragment;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;

public class InLocationActivity extends AppCompatActivity {

    //Фрагменты, между которыми можно переключаться
    InLocationFragment locationFragment;
    MapFragment mapFragment;
    StatsFragment statsFragment;

    //Текущая локация, игрок, очки прокачки доступные игроку
    Location location;
    Player player;
    Integer upgradePoints = 30;

    //фончик
    ProportionalImageView imageView;

    //Мир (внутренняя логика)
    private World gameWorld = SimpleWorldClass.generateWorld();

    //Ключи для сохранения в Bundle
    static final String IN_LOC_WORLD_ID = "world";
    static final String IN_LOC_PLAYER_ID = "player";
    static final String IN_LOC_LOCATION_ID = "location";
    static final String IN_LOC_UPG_ID = "upgrade points";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_location);

        //Пихаем фончик за весь наш интерфейс, задаём ему немного прозрачности
        imageView = new ProportionalImageView(this);
        imageView.setAlpha(0.4f);
        FrameLayout mainFrame = findViewById(R.id.activity_in_location_backgroundFrame);
        mainFrame.addView(imageView, 0);

        //Достаём игрока которого создали в прошлом активити из интента
        player = (PlayerClass) getIntent().getSerializableExtra("player");

        //Создаём наши фрагменты, изначально суём на экран фрагмент локации
        locationFragment = InLocationFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activity_in_location_fragHolder, locationFragment)
                .commit();

        mapFragment = MapFragment.newInstance();
        statsFragment = StatsFragment.newInstance();

        //Берём случайную локацию из мира и пихаем туда игрока
        location = gameWorld.getRandomLocation();
        transitionToNewLocation(location);

        //Достаём кнопочки и вешаем события
        Button toMenu = findViewById(R.id.activity_in_location_menu_toMenu);
        toMenu.setOnClickListener(v -> goToMenu());
        Button toLoc = findViewById(R.id.activity_in_location_menu_loc);
        toLoc.setOnClickListener(v -> changeFragment(locationFragment));
        Button toMap = findViewById(R.id.activity_in_location_menu_map);
        toMap.setOnClickListener(v -> changeFragment(mapFragment));
        Button toStats = findViewById(R.id.activity_in_location_menu_statistics);
        toStats.setOnClickListener(v -> changeFragment(statsFragment));

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(IN_LOC_WORLD_ID, gameWorld);
        savedInstanceState.putSerializable(IN_LOC_LOCATION_ID, location);
        savedInstanceState.putSerializable(IN_LOC_PLAYER_ID, player);
        savedInstanceState.putInt(IN_LOC_UPG_ID, upgradePoints);
        GameLogger.log("GDCN", "InLocation instance Saved");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        gameWorld = (SimpleWorldClass) savedInstanceState.getSerializable(IN_LOC_WORLD_ID);
        location = (SimpleLocationClass) savedInstanceState.getSerializable(IN_LOC_LOCATION_ID);
        player = (PlayerClass)savedInstanceState.getSerializable(IN_LOC_PLAYER_ID);
        upgradePoints = savedInstanceState.getInt(IN_LOC_UPG_ID);
        GameLogger.log("GDCN", "InLocation instance Restored");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Обновляем контент
        locationFragment.updateContent();
    }

    private void changeFragment(Fragment fragment) {
        //Заменяем фрагмент в нашем холдере на тот который нужно
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_in_location_fragHolder, fragment);
        fragmentTransaction.commit();
    }

    public void goToMenu() {
        //Уходим в меню, опусташая стэк (Menu = singleTask)
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void transitionToNewLocation(Location location) {

        //Логика перехода в другую локацию

        //Говорим локации что ушли
        if (this.location != null)
            this.location.playerGone();
        //Говорим игроку что он ушёл
        player.changeLocationTo(location);
        //Сохраняем новую локацию в поле
        this.location = location;
        //Говорим ей что игрок пришёл
        location.playerCame(player);
        //Меняем фон в зависимости от типа локации
        changeBackground(location.getType());
        //Если карта на экране, сразу обновим её
        if (mapFragment.isAdded())
            mapFragment.updateMap();
        //Задаём фрагменту с инфой о локации новую локацию и скажем ему обновить содержимое
        locationFragment.setCurrentLocation(location);
        locationFragment.updateContent();
    }

    public void goFurther(MapDirection direction) {
        //Переходим по определённому направлению (после клика стрелочки)
        //Проверку наличия локации по этому направлению совершаем раньше
        transitionToNewLocation(location.getNeightbour(direction));
    }

    private SimpleLocationClass generateLocation() {
        //Ровно то что написано в названии метода, использовался до создания нормального World
        return new SimpleLocationClass();
    }

    public void goToFight() {

        //Начинаем бой с противником в локации

        //Создаём интент
        Intent intent = new Intent(this, FightActivity.class);
        //Закидываем туда наших персонажей и тип локации, чтобы установить фон
        intent.putExtra("enemy", location.getNPC());
        intent.putExtra("player", player);
        intent.putExtra("locationType", location.getType());
        //Говорим что нам нужен ответ от активити, чтобы узнать кто победил. Реквест код задаётся
        //от балды (пока что)
        startActivityForResult(intent, 1);
    }

    public void changeBackground(LocationType type) {
        //Меняем картинку заднему фону в зависимости от типа локации
        int image;
        switch (type) {
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
        //Создаём асинхронный таск, чтобы не загружать основной поток
        //В нём загружаем ресурс картинки, потом подпихиваем на фон
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Drawable drawable = getDrawableByIndex(image);
                runOnUiThread(() -> imageView.setImageDrawable(drawable));
                return null;
            }
        };
        task.execute(image);
    }

    private Drawable getDrawableByIndex(int image) {
        //шорткат
        return imageView.getResources().getDrawable(image);
    }

    public World getWorld() {
        return gameWorld;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //В зависимости от результата боя совершаем нужные действия
        switch (resultCode) {
            case FightResult.PLAYER_LOST:
                //Игрок умер, уходим в меню
                goToMenu();
                break;
            case FightResult.ALL_ALIVE:
                break;
            case FightResult.PLAYER_WON:
                //Игрок выиграл, дать ему очков и больше нельзя драться с этим врагом
                //Он ведь умер!
                upgradePoints += 3;
                setEnemyDead();
                break;
        }
    }

    private void setEnemyDead() {
        if (location == null)
            return;
        if (location.getNPC() == null)
            return;
        location.getNPC().kill();
    }


    public Integer getUpgradePoints() {
        return upgradePoints;
    }

    public void decreaseUpgradePoints() {
        upgradePoints--;
    }

    public Player getPlayer() {
        return player;
    }
}
