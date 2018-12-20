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

import ru.gdcn.beastmaster64revelations.FightActivity;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleLocationClass;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleWorldClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Fight.FightResult;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;
import ru.gdcn.beastmaster64revelations.MainMenu.MainMenuActivity;
import ru.gdcn.beastmaster64revelations.MapFragment;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.StatsFragment;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;

public class InLocationActivity extends AppCompatActivity {

    InLocationFragment locationFragment;
    MapFragment mapFragment;
    StatsFragment statsFragment;

    Location location;
    Player player;
    Integer upgradePoints = 5;

    ProportionalImageView imageView;

    private World gameWorld = SimpleWorldClass.generateWorld();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_location);
        imageView = new ProportionalImageView(this);
        imageView.setAlpha(0.4f);


        FrameLayout mainFrame = findViewById(R.id.activity_in_location_backgroundFrame);
        mainFrame.addView(imageView, 0);

        player = (PlayerClass) getIntent().getSerializableExtra("player");

        locationFragment = InLocationFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activity_in_location_fragHolder, locationFragment)
                .commit();

        mapFragment = MapFragment.newInstance();
        statsFragment = StatsFragment.newInstance();

        location = gameWorld.getRandomLocation();
        transitionToNewLocation(location);

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
    protected void onPostResume() {
        super.onPostResume();
        locationFragment.updateContent();
    }

    private void changeFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_in_location_fragHolder, fragment);
        fragmentTransaction.commit();

    }

    public void goToMenu(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void transitionToNewLocation(Location location) {
        if (this.location != null)
            this.location.playerGone();
        player.changeLocationTo(location);
        this.location = location;
        location.playerCame(player);
        changeBackground(location.getType());
        if (mapFragment.isAdded())
            mapFragment.updateMap();
        locationFragment.setCurrentLocation(location);
        locationFragment.updateContent();
    }

    public void goFurther(MapDirection direction){
        transitionToNewLocation(location.getNeightbour(direction));
    }

    private SimpleLocationClass generateLocation() {
        return new SimpleLocationClass();
    }

    public void goToFight() {
        Intent intent = new Intent(this, FightActivity.class);
        intent.putExtra("enemy", location.getNPC());
        intent.putExtra("player", player);
        intent.putExtra("locationType", location.getType());
        startActivityForResult(intent, 1);
    }

    public void changeBackground(LocationType type){
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
        return imageView.getResources().getDrawable(image);
    }

    public World getWorld() {
        return gameWorld;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case FightResult.PLAYER_LOST:
                goToMenu();
                break;
            case FightResult.ALL_ALIVE:
                break;
            case FightResult.PLAYER_WON:
                upgradePoints+=3;
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
