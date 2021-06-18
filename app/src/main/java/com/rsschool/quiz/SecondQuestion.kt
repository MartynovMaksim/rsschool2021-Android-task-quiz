package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.databinding.FragmentSecondQuestionBinding

private var _binding: FragmentSecondQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator

class SecondQuestion : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = activity as Communicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(PREVIOUS_RADIOBUTTON_ID)
        with(binding) {
            this.question.text = "Какой океан является самым большим?"
            this.optionOne.text = "Южный"
            this.optionTwo.text = "Атлантический"
            this.optionThree.text = "Индийский"
            this.optionFour.text = "Северный Ледовитый"
            this.optionFive.text = "Тихий"
            this.previousButton.setOnClickListener {
//                id?.let {communicator.openFirstQuestion(it)}
                communicator.openFirstQuestion()
            }
            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
            }
            nextButton.setOnClickListener {
                radiobuttonId?.let { communicator.openThirdQuestion(it) }
            }
            val id = arguments?.getInt(PREVIOUS_RADIOBUTTON_ID)
            if (id != -1) {
                id?.let {
                    (radioGroup.getChildAt(it) as RadioButton).isChecked = true }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(radioButtonId: Int): SecondQuestion {
            val fragment = SecondQuestion()
            val args = Bundle()
            args.putInt(PREVIOUS_RADIOBUTTON_ID, radioButtonId)
            fragment.arguments = args
            return fragment

        }

        private const val PREVIOUS_RADIOBUTTON_ID = "RADIOBUTTON_ID"
    }
}