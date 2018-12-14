package ru.gdcn.beastmaster64revelations.GameActivities.InLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.FightActivity;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleLocationClass;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.R;

public class InLocationFragment extends Fragment {

    Location currentLocation;

    public InLocationFragment() {
        // Required empty public constructor
    }

    public static InLocationFragment newInstance() {
        InLocationFragment fragment = new InLocationFragment();
        Bundle args = new Bundle();
        //args.putObject("atributeNames", stuff);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_in_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContent(currentLocation);
        Button attackButton = getActivity().findViewById(R.id.fragment_in_location_content_NPCattack);
        attackButton.setOnClickListener(v -> {
            attackNPC();
        });
        Button talkButton = getActivity().findViewById(R.id.fragment_in_location_content_NPCtalk);
        talkButton.setOnClickListener(v -> {
            talkNPC();
        });
        Button goAwayButton = getActivity().findViewById(R.id.fragment_in_location_content_GoAway);
        goAwayButton.setOnClickListener(v -> {
            goAway();
        });
        setContent(currentLocation);

        //this.setContent(new SimpleLocationClass(new MapPoint(0,0)));
    }

    private void goAway() {
        InLocationActivity activity = (InLocationActivity) getActivity();
        activity.goFurther();
    }

    private void talkNPC() {
        //TODO
    }

    private void attackNPC() {
        InLocationActivity activity = (InLocationActivity) getActivity();
        activity.goToFight();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            //atributes = getArguments().getStringArray("atributeNames");
        }


    }

    public void setContent(Location location){
        TextView nameView = getActivity().findViewById(R.id.fragment_in_location_content_LocationName);
        nameView.setText(location.getName());
        TextView descView = getActivity().findViewById(R.id.fragment_in_location_content_LocationDescriptionContent);
        descView.setText(location.getDescription());

        TextView npcName = getActivity().findViewById(R.id.fragment_in_location_content_NPCTitle);
        npcName.setText(location.getNPC().getName());
        TextView npcDesc = getActivity().findViewById(R.id.fragment_in_location_content_NPCDescription);
        npcDesc.setText(location.getNPC().toString());
    }


    public void setCurrentLocation(Location location) {
        currentLocation = location;
    }

    public void updateContent() {
        if (isAdded())
            setContent(currentLocation);
    }
}