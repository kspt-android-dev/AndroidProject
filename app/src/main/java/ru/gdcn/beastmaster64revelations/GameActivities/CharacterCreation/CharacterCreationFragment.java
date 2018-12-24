package ru.gdcn.beastmaster64revelations.GameActivities.CharacterCreation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.R;

public class CharacterCreationFragment extends Fragment {

    //НЕ ИСПОЛЬЗУЕТСЯ В ДАННОЙ ВЕРСИИ

    String[] atributes;
    Integer totalPoints;
    AttributeChanger[] changers;
    AttributeCounter counter;
    TextView inputField;

    public CharacterCreationFragment() {
        // Required empty public constructor
    }

    public static CharacterCreationFragment newInstance(String[] atributeNames, Integer totalPoints) {
        CharacterCreationFragment fragment = new CharacterCreationFragment();
        Bundle args = new Bundle();
        args.putStringArray("atributeNames", atributeNames);
        args.putInt("totalPoints", totalPoints);
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
        return inflater.inflate(R.layout.fragment_character_creation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            atributes = getArguments().getStringArray(getString(R.string.character_creation_text_attributeNames));
            totalPoints = getArguments().getInt(getString(R.string.character_creation_text_totalPoints));
        }

        changers = new AttributeChanger[atributes.length];

        inputField = getActivity().findViewById(R.id.fragment_character_creation_nameField);
        final TextView title = getActivity().findViewById(R.id.fragment_character_creation_title);
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title.setText(s);
                if (s.length() == 0)
                    title.setText(R.string.main_character_name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView totalPointsShower = getActivity().findViewById(R.id.fragment_character_creation_totalPointsText);
        totalPointsShower.setText(String.valueOf(totalPoints));
        counter = new AttributeCounter(totalPoints, totalPointsShower);

        LinearLayout attrHolder = getActivity().findViewById(R.id.fragment_character_creation_attributeChangersLayout);

        for (int i = 0; i < atributes.length; i++){
            changers[i] = new AttributeChanger(getContext(), null, atributes[i], counter);
            counter.addChanger(changers[i]);
            attrHolder.addView(changers[i]);
        }

        counter.notifyChangers();

    }

    public boolean hasPoints(){
        return counter.hasPoints();
    }

    public boolean hasName(){
        return inputField.getText().length() > 0;
    }

//    public Player getCharacterFromData() {
//        Player player = new PlayerClass(inputField.getText().toString(), null, changers[0].getPoints(), changers[1].getPoints(), changers[2].getPoints(), 10, gender);
//        return player;
//    }
}
