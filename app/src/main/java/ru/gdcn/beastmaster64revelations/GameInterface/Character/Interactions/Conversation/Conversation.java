package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Conversation;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Interaction;

public interface Conversation extends Interaction {

    /** Conversation отвечает за общение с пассивными NPC, такими, как торговцы
     * и странники. **/

    /**
     * Возвращает Персонажа, с которым общается Игрок
     **/
    public Character getOtherCharacter();

    /**
     * Возвращает игрока, находящегося в разговоре.
     **/
    public Character getPlayer();

    /**
     * Отправляет собеседнику вариант диалога и возвращает ответ DialogReply.
     **/
    public DialogReply conversate(DialogVariant words);

}
