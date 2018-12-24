package ru.gdcn.beastmaster64revelations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClassTest;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleGameMapClassTest;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleLocationClassTest;
import ru.gdcn.beastmaster64revelations.GameClass.WorldElemets.SimpleWorldClassTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleWorldClassTest.class,
        SimpleLocationClassTest.class,
        SimpleGameMapClassTest.class,
        CharacterClassTest.class
})

public class MainTest {
    //Run me!!!
}