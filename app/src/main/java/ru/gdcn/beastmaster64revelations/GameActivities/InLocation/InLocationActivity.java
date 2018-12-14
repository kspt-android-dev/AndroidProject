package ru.gdcn.beastmaster64revelations.GameActivities.InLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import ru.gdcn.beastmaster64revelations.FightActivity;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleLocationClass;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;

public class InLocationActivity extends AppCompatActivity {

    ProportionalImageView imageView;
    InLocationFragment locationFragment;
    Location location;
    PlayerClass player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_location);
        imageView = new ProportionalImageView(this);
        imageView.setAlpha(0.55f);

        FrameLayout mainFrame = findViewById(R.id.activity_in_location_backgroundFrame);
        mainFrame.addView(imageView, 0);

        player = (PlayerClass) getIntent().getSerializableExtra("player");

        locationFragment = InLocationFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activity_in_location_fragHolder, locationFragment)
                .commit();

        location = generateLocation();
        transitionToNewLocation(location);

        Button toMenu = findViewById(R.id.activity_in_location_manu_settings);
        toMenu.setOnClickListener(v -> {
            //TODO GOBACK
        });
    }

    private void transitionToNewLocation(Location location) {
        player.changeLocationTo(location);
        this.location = location;
        location.playerCame(player);
        changeBackground(location.getType());
        locationFragment.setCurrentLocation(location);
        locationFragment.updateContent();
    }

    public void goFurther(){
        transitionToNewLocation(generateLocation());
    }

    private SimpleLocationClass generateLocation() {
        return new SimpleLocationClass();
    }

    public void goToFight() {
        Intent intent = new Intent(this, FightActivity.class);
        intent.putExtra("enemy", location.getNPC());
        intent.putExtra("player", player);
        intent.putExtra("locationType", location.getType());
        startActivity(intent);
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
        imageView.setImageDrawable(imageView.getResources().getDrawable(image));
    }

}
