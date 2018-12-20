package ru.gdcn.beastmaster64revelations;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;

import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

public class MapButton extends android.support.v7.widget.AppCompatButton {

    Location location;

    public MapButton(Context context, Location location) {
        super(context);

        this.location = location;

        GridLayout.LayoutParams doubleLayoutParams = new GridLayout.LayoutParams();
        doubleLayoutParams.width = 0;
        doubleLayoutParams.height = 0;
        doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 5f);
        doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 5f);
        doubleLayoutParams.setGravity(Gravity.FILL);
        setLayoutParams(doubleLayoutParams);

        setOnClickListener(v -> {
            InLocationActivity activity = (InLocationActivity) getContext();
            activity.transitionToNewLocation(location);
        });
//        super(context, null, R.style.MapElement);
    }
}
