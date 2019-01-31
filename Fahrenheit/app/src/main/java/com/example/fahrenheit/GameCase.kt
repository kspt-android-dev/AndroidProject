package com.example.fahrenheit

open class GameCase(val type: TypeCase)
enum class TypeCase {
    TEXT,
    QUESTION,
    BUTTON_TEXT,
    LINK,
    BRIDGE,
    PROGRAM_LOGIC
}

data class TextCase(val text: String, val typeCase: TypeCase, val linkID: Int) : GameCase(typeCase)
data class QuestionCase(val text: String, val typeCase: TypeCase, val linkID: Int) : GameCase(typeCase)
data class ButtonCase(val buttonEvents: List<Pair<String, Int>>, val typeCase: TypeCase, val linkID: Int) :
    GameCase(typeCase)
data class BridgeCase(val targetId: Int, val typeCase: TypeCase, val linkID: Int) : GameCase(typeCase)
data class ProgramCase(
    val eventType: ProgramType,
    val programEvents: List<Int>?, val typeCase: TypeCase, val linkID: Int) : GameCase(typeCase)
enum class ProgramType{
    MUSIC,
    DIFFERENT,
    QUIT
}