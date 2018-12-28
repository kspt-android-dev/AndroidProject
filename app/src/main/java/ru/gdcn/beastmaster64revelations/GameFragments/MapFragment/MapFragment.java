package ru.gdcn.beastmaster64revelations.GameFragments.MapFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;
import ru.gdcn.beastmaster64revelations.R;


public class MapFragment extends Fragment {

    final static int MAP_Z = 20;

    public MapFragment() {}

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateMap();
    }

    @SuppressWarnings("ConstantConditions")
    public void updateMap() {
        //noinspection ConstantConditions
        World world = ((InLocationActivity)(getActivity())).getWorld();

        if (world == null)
            return;

        GameMap map = world.getGameMap();

        Log.d("MAP", "UPDATING");

        GridLayout gridLayout = getActivity().findViewById(R.id.fragment_map_holder);
        gridLayout.removeAllViews();
        gridLayout.setZ(MAP_Z);


        gridLayout.setColumnCount(map.getWidth());

        for (int i = 0; i < map.getHeight() * map.getWidth(); i++){
            Log.d("MAP", "ITERATING");
            Location location = map.getLocationAt(new MapPoint(i % map.getWidth(), i / map.getWidth()));
            MapButton button = new MapButton(getActivity(), location);
//            Button button = new Button(new ContextThemeWrapper(getActivity(), R.style.MapElement), null, 0);
//            Button button = new Button(getActivity());
//            Button button = (Button)getLayoutInflater().inflate(R.layout.test_button_layout, null);

            if (location.hasPlayer())
                button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.has_player));
            else{
                if (location.getNPC() != null && location.getNPC().isDead())
                    button.setAlpha(0.25f);
                switch (location.getType()){
                    case FLATLAND:
                        Log.d("MAP", "COLORING");
                        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.map_flatland));
                        break;
                    case FOREST:
                        Log.d("MAP", "COLORING");
                        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.map_forest));
                        break;
                    case ROCK:
                        Log.d("MAP", "COLORING");
                        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.map_rock));
                        break;
                    default:
                        Log.d("MAP", "COLORING");
                        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.map_default));
                        break;
                }
            }
            gridLayout.addView(button);
        }
    }

}
