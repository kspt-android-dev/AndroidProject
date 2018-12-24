package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Conversation;

public interface DialogVariant {

    /**
     * Возвращает текст варианта диалога.
     **/
    public String getText();

    /**
     * Возвращает тип варианта диалога.
     **/
    public DialogType getType();

}
