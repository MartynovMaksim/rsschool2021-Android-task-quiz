package com.rsschool.quiz

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val APP_PREFERENCES = "APP_PREFERENCES"

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        preferences.edit().clear().apply()
        setContentView(R.layout.activity_main)
//        openFirstQuestion(-1)
        openFirstQuestion()
    }

    override fun openFirstQuestion() {
//        val firstQuestion = FirstQuestion.newInstance(radioButtonId)
        val firstQuestion = FirstQuestion()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, firstQuestion).commit()
    }

    override fun openSecondQuestion() {
        val secondQuestion = SecondQuestion()
//        val secondQuestion = SecondQuestion.newInstance(radioButtonId)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, secondQuestion).commit()
    }

    override fun openThirdQuestion(radioButtonId: Int) {
        val thirdQuestion = ThirdQuestion.newInstance(radioButtonId)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, thirdQuestion).commit()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}