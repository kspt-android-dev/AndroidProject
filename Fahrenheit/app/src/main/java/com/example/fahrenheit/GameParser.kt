package com.example.fahrenheit

import android.util.Log
import java.io.InputStream
import java.lang.Exception

fun parseFile(inputStream: InputStream):List<GameCase?> {
    val inputAsString = inputStream.bufferedReader().use { it.readText() }
    val stringList = inputAsString.split("\n")
    val gameCaseList = mutableListOf<GameCase?>()
    for (i in 0 until stringList.size) {
        val string = stringList[i].trim()
        when {
            string.first() == '~' -> gameCaseList.add(QuestionCase(string.substring(1), TypeCase.QUESTION, i))
            string.first() == '@' -> gameCaseList.add(BridgeCase(string.substring(1).toInt(), TypeCase.BRIDGE, i))
            string.first() == '#' -> {
                if (i < stringList.size - 1) {
                    val nextString = stringList[i + 1].trim()
                    val caseLinks = nextString.split('%')
                    val links = mutableListOf<Int>()
                    for (link in caseLinks) {
                        links.add(link.toInt())
                    }
                    gameCaseList.add(ProgramCase(links, TypeCase.PROGRAM_LOGIC, i))
                } else {
                    throw Exception("Uncorrected using programmatically case")
                }
            }
            string.contains('|') -> {
                if (i < stringList.size - 1) {
                    val nextString = stringList[i + 1].trim()
                    val caseQuests = string.split('|')
                    val caseLinks = nextString.split('%')
                    val links = mutableListOf<Int>()
                    for (link in caseLinks) {
                        links.add(link.toInt())
                    }
                    val result = mutableListOf<Pair<String, Int>>()
                    for (c in 0 until caseQuests.size){
                        result.add(Pair(caseQuests[c], links[c]))
                    }
                    gameCaseList.add(ButtonCase(result, TypeCase.BUTTON_TEXT, i))
                } else {
                }
            }
            string.contains('%') -> gameCaseList.add(null)
            else -> gameCaseList.add(TextCase(string, TypeCase.TEXT, i))
        }

        if (gameCaseList[i] == null)
            Log.i("GAME", "type = " + null)
        else
            Log.i("GAME", "type = " + gameCaseList[i]!!.type.toString())
    }
    return gameCaseList
}

