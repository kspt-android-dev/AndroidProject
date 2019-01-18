package lestwald.insectkiller

import android.content.ContentValues
import android.content.Context
import java.util.*

val listOfName = listOf("Domenic", "Lou", "Matt", "Bennie", "Ellis", "Cleveland",
        "Stefan", "Adolph", "Chester", "Sung", "Buck", "Julian", "Andrea", "Brain",
        "Edward", "Beau", "Whitney", "Harold", "Milan", "Brandon", "Britni", "Erna",
        "Santa", "Georgie", "Alberta", "Lucinda", "January", "Loni", "Rona", "Vivien")

fun addScore(context: Context, dbName: String, name: String, score: Int) {
    val dbHelper = DBHelper(context, dbName)
    val database = dbHelper.writableDatabase
    val contentValues = ContentValues()
    contentValues.put(DBHelper.KEY_NAME, name)
    contentValues.put(DBHelper.KEY_SCORE, score)
    database.insert(dbName, null, contentValues)
}

fun addDefaultScores(context: Context, dbName: String) {
    val random = Random()
    var maxScore = 1000
    if (dbName == "leaderboard2") maxScore = 600
    else if (dbName == "leaderboard3") maxScore = 200
    for (i in 0..9) {
        addScore(context, dbName, listOfName[random.nextInt(29)], random.nextInt(maxScore))
    }
}