package com.rsschool.quiz

interface Communicator {
    fun openFirstQuestion()
    fun openSecondQuestion()
//    fun openFirstQuestion(radioButtonId: Int)
//    fun openSecondQuestion(radioButtonId: Int)
    fun openThirdQuestion(radioButtonId: Int)

}