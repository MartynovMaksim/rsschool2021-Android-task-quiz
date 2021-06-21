package com.rsschool.quiz

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val APP_PREFERENCES = "APP_PREFERENCES"
var PREF_FIRST_QUEST_ANSWER_ID = "PREF_FIRST_QUEST_ANSWER_ID"
var PREF_FIRST_QUEST_ANSWER_VALUE = "PREF_FIRST_QUEST_ANSWER_VALUE"
const val FIRST_QUESTION = "Какой город является столицей России?"
val firstQuestion = arrayOf("Москва", "Санкт-Петербург", "Самара", "Казань", "Мытищи")
var PREF_SECOND_QUEST_ANSWER_ID = "PREF_SECOND_QUEST_ANSWER_ID"
var PREF_SECOND_QUEST_ANSWER_VALUE = "PREF_SECOND_QUEST_ANSWER_VALUE"
const val SECOND_QUESTION = "Какой океан является самым большим?"
val secondQuestion =
    arrayOf("Южный", "Атлантический", "Индийский", "Северный Ледовитый", "Тихий")
var PREF_THIRD_QUEST_ANSWER_ID = "PREF_THIRD_QUEST_ANSWER_ID"
var PREF_THIRD_QUEST_ANSWER_VALUE = "PREF_THIRD_QUEST_ANSWER_VALUE"
const val THIRD_QUESTION = "Какая планета является пятой в Солнечной системе?"
val thirdQuestion = arrayOf("Уран", "Нептун", "Сатурн", "Юпитер", "Марс")
var PREF_FOURTH_QUEST_ANSWER_ID = "PREF_FOURTH_QUEST_ANSWER_ID"
var PREF_FOURTH_QUEST_ANSWER_VALUE = "PREF_FOURTH_QUEST_ANSWER_VALUE"
const val FOURTH_QUESTION = "Кто первым вышел в открытый космос?"
val fourthQuestion = arrayOf("Гагарин Ю.А", "Титов Г.С", "Терешкова В.В", "Леонов А.А", "Гречко Г.М")
var PREF_FIFTH_QUEST_ANSWER_ID = "PREF_FIFTH_QUEST_ANSWER_ID"
var PREF_FIFTH_QUEST_ANSWER_VALUE = "PREF_FIFTH_QUEST_ANSWER_VALUE"
const val FIFTH_QUESTION = "Сколько пальцев суммарно должно быть у кошки/кота?"
val fifthQuestion = arrayOf("14","16","18","20","22")

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        preferences.edit().clear().apply()
        setContentView(R.layout.activity_main)
        openFirstQuestion()
    }

    override fun openFirstQuestion() {
        val firstQuestion = FirstQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, firstQuestion).commit()
    }

    override fun openSecondQuestion() {
        val secondQuestion = SecondQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, secondQuestion).commit()
    }

    override fun openThirdQuestion() {
        val thirdQuestion = ThirdQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, thirdQuestion).commit()
    }

    override fun openFourthQuestion() {
        val fourthQuestion = FourthQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fourthQuestion).commit()
    }

    override fun openFifthQuestion() {
        val fifthQuestion = FifthQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fifthQuestion).commit()
    }

    override fun scoreFragment() {
        val scoreFragment = Score()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, scoreFragment).commit()
    }

}