package ru.gdcn.beastmaster64revelations.GameClass.Characters;

import org.junit.Test;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;

import static org.junit.Assert.*;

public class CharacterClassTest {

    private Character character = new CharacterClass("TestCharacter", null, 10, 10, 10, 10);

    @Test
    public void getMaxHP() {
        assertNotNull(character.getMaxHP());
        int old = character.getMaxHP();
        character.gainStrength(10);
        assertTrue(old < character.getMaxHP());
    }

    @Test
    public void getName() {
        assertNotNull(character.getName());
        assertTrue(character.getName().length() > 0);
    }

    @Test
    public void getHP() {
        assertNotNull(character.getHP());
        assertEquals(character.getMaxHP(), character.getHP());
        assertTrue(character.dealPhysicalDamage(1));
        assertTrue(character.getHP() < character.getMaxHP());
    }

    @Test
    public void getStrength() {
        assertNotNull(character.getStrength());
        int old = character.getStrength();
        assertTrue(character.gainStrength(20));
        assertTrue(character.getStrength() > old);
    }

    @Test
    public void getAgility() {
        assertNotNull(character.getAgility());
        int old = character.getAgility();
        assertTrue(character.gainAgility(20));
        assertTrue(character.getAgility() > old);
    }

    @Test
    public void getBasicAttack() {
        assertNotNull(character.getBasicAttack());
        assertTrue(character.getBasicAttack() > 0);
    }

    @Test
    public void getIntellect() {
        assertNotNull(character.getIntellect());
        int old = character.getIntellect();
        assertTrue(character.gainIntellect(20));
        assertTrue(character.getIntellect() > old);
    }

    @Test
    public void getFullAttack() {
        assertNotNull(character.getFullAttack());
        assertTrue(character.getFullAttack() > 0);
    }

    @Test
    public void dealHeal() {
        assertNotNull(character.getHP());
        assertEquals(character.getMaxHP(), character.getHP());
        assertTrue(character.dealPhysicalDamage(1));
        assertTrue(character.getHP() < character.getMaxHP());
        assertTrue(character.dealHeal(1));
        assertEquals(character.getHP(), character.getMaxHP());
    }

    @Test
    public void dealPhysicalDamage() {
        assertNotNull(character.getHP());
        assertEquals(character.getMaxHP(), character.getHP());
        assertTrue(character.dealPhysicalDamage(1));
        assertTrue(character.getHP() < character.getMaxHP());
    }

    @Test
    public void dealMagicalDamage() {
        assertNotNull(character.getHP());
        assertEquals(character.getMaxHP(), character.getHP());
        assertTrue(character.dealMagicalDamage(1));
        assertTrue(character.getHP() < character.getMaxHP());
    }

    @Test
    public void isDead() {
        character.dealPhysicalDamage(character.getMaxHP());
        assertTrue(character.isDead());
    }

    @Test
    public void kill() {
        character.kill();
        assertTrue(character.isDead());
    }

}