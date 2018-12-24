package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Conversation;

import java.util.List;

public interface DialogReply {

    /** Возвращает список возможных ответов **/
    public List<DialogVariant> getPossibleAnswers();

    /** Возвращает текст, сказанный собеседником **/
    public String getText();

}
