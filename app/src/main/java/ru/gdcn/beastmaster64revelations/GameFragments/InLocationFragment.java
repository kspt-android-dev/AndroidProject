package ru.gdcn.beastmaster64revelations.GameFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.R;

public class InLocationFragment extends Fragment {

    private Button goUpButton;
    private Button goDownButton;
    private Button goLeftButton;
    private Button goRightButton;

    private Button fightNPC;
    private Button talkNPC;

    private Location currentLocation;

    final static String IN_LOC_FRAG_LOCATION_ID = "location in frag";
    final static int LOC_DIFFICULTY_MODIFIER = 10;
    final static double LOC_DIFFICULTY_DIVIDER = 10.0;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_location, container, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fightNPC = getActivity().findViewById(R.id.fragment_in_location_content_NPCattack);
        fightNPC.setOnClickListener(v -> attackNPC());
        talkNPC = getActivity().findViewById(R.id.fragment_in_location_content_NPCtalk);
        talkNPC.setOnClickListener(v -> talkNPC());

        goUpButton = getActivity().findViewById(R.id.fragment_in_location_content_GoUp);
        goDownButton = getActivity().findViewById(R.id.fragment_in_location_content_GoDown);
        goLeftButton = getActivity().findViewById(R.id.fragment_in_location_content_GoLeft);
        goRightButton = getActivity().findViewById(R.id.fragment_in_location_content_GoRight);

        goDownButton.setOnClickListener(v -> goAway(MapDirection.DOWN));
        goUpButton.setOnClickListener(v -> goAway(MapDirection.UP));
        goLeftButton.setOnClickListener(v -> goAway(MapDirection.LEFT));
        goRightButton.setOnClickListener(v -> goAway(MapDirection.RIGHT));

        setContent(currentLocation);

        //this.setContent(new SimpleLocationClass(new MapPoint(0,0)));
    }

    @SuppressWarnings("ConstantConditions")
    private void goAway(MapDirection direction) {
        InLocationActivity activity = (InLocationActivity) getActivity();
        activity.goFurther(direction);
    }

    private void talkNPC() {
        //TODO
    }

    @SuppressWarnings("ConstantConditions")
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

    @SuppressWarnings("ConstantConditions")
    public void setContent(Location location) {
        this.currentLocation = location;
        updateButtons();

        if (location == null)
            return;

        if (location.hasNPC()) {
            TextView npcName = getActivity().findViewById(R.id.fragment_in_location_content_NPCTitle);
            String text;
            if (location.getNPC().isDead()) {
                text = location.getNPC().getName() + getString(R.string.in_location_fragment_dead);
                npcName.setText(text);
            } else
                npcName.setText(location.getNPC().getName());
            TextView npcDesc = getActivity().findViewById(R.id.fragment_in_location_content_NPCDescription);
            npcDesc.setText(location.getNPC().getDescription());
            fightNPC.setEnabled(location.getNPC().isAttackable() && !location.getNPC().isDead());
            talkNPC.setEnabled(location.getNPC().isTalkable() && !location.getNPC().isDead());
        }

        TextView diffText = getActivity().findViewById(R.id.fragment_in_location_content_difficulty);
        diffText.setText(String.valueOf(((int) (location.getDifficulty() * LOC_DIFFICULTY_MODIFIER)) / LOC_DIFFICULTY_DIVIDER));
        TextView nameView = getActivity().findViewById(R.id.fragment_in_location_content_LocationName);
        nameView.setText(location.getName());
        TextView descView = getActivity().findViewById(R.id.fragment_in_location_content_LocationDescriptionContent);
        descView.setText(location.getDescription());
    }

    private void updateButtons() {

        goDownButton.setEnabled(false);
        goLeftButton.setEnabled(false);
        goRightButton.setEnabled(false);
        goUpButton.setEnabled(false);

        if (currentLocation == null)
            return;

        if (currentLocation.getNeightbour(MapDirection.LEFT) != null)
            goLeftButton.setEnabled(true);
        if (currentLocation.getNeightbour(MapDirection.RIGHT) != null)
            goRightButton.setEnabled(true);
        if (currentLocation.getNeightbour(MapDirection.DOWN) != null)
            goDownButton.setEnabled(true);
        if (currentLocation.getNeightbour(MapDirection.UP) != null)
            goUpButton.setEnabled(true);

    }


    public void setCurrentLocation(Location location) {
        currentLocation = location;
    }

    public void updateContent() {
        if (isAdded())
            setContent(currentLocation);
    }
}