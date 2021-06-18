package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup




class ThirdQuestion : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_question, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(radioButtonId: Int): ThirdQuestion {
            val fragment = ThirdQuestion()
            val args = Bundle()
            args.putInt(PREVIOUS_RADIOBUTTON_ID_FRAGMENT_1, radioButtonId)
            args.putInt(PREVIOUS_RADIOBUTTON_ID_FRAGMENT_2, radioButtonId)
            fragment.arguments = args
            return fragment

        }

        private const val PREVIOUS_RADIOBUTTON_ID_FRAGMENT_1 = "RADIOBUTTON_ID"
        private const val PREVIOUS_RADIOBUTTON_ID_FRAGMENT_2 = "RADIOBUTTON_ID"
    }
}