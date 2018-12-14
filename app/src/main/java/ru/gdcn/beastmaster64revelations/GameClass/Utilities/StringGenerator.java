package ru.gdcn.beastmaster64revelations.GameClass.Utilities;

import ru.gdcn.beastmaster64revelations.GameClass.Constants.Strings;
import ru.gdcn.beastmaster64revelations.GameClass.Items.Equipment.Weapons.WeaponType;
import ru.gdcn.beastmaster64revelations.GameClass.Utilities.Utilities;

import java.util.Random;

public class StringGenerator {

    public static final Random randomGenerator = new Random();

    public static String weaponAdjective() {
        return (String) Utilities.getRandomElement(Strings.WEAPON_ADJECTIVES, randomGenerator);
    }

    public static String weaponMainName(WeaponType weaponType) {
        switch (weaponType){
            case AXE:
                return (String) Utilities.getRandomElement(Strings.WEAPON_MAINNAME_AXE, randomGenerator);
            case SWORD:
                return (String) Utilities.getRandomElement(Strings.WEAPON_MAINNAME_SWORD, randomGenerator);
            case KNIFE:
                return (String) Utilities.getRandomElement(Strings.WEAPON_MAINNAME_KNIFE, randomGenerator);
                default:
                    return Strings.WEAPON_DEFAULT_WEAPON_NAME;
        }
    }

    public static String weaponPostName() {
        return (String) Utilities.getRandomElement(Strings.WEAPON_POSTNAME, randomGenerator);
    }

}
