package com.rsschool.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import com.rsschool.quiz.databinding.FragmentThirdQuestionBinding

private var _binding: FragmentThirdQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator


class ThirdQuestion : Fragment() {

    private lateinit var preferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = activity as Communicator
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = requireNotNull(context?.getColor(R.color.light_green_100_dark))
        preferences =
            requireNotNull(activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        _binding = FragmentThirdQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nextButton.isEnabled = false
            question.text = THIRD_QUESTION
            optionOne.text = thirdQuestion[0]
            optionTwo.text = thirdQuestion[1]
            optionThree.text = thirdQuestion[2]
            optionFour.text = thirdQuestion[3]
            optionFive.text = thirdQuestion[4]

            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
                nextButton.isEnabled = true
            }
            previousButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openSecondQuestion()
            }
            toolbar.setNavigationOnClickListener {
                saveResults(radiobuttonId)
                communicator.openSecondQuestion()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                saveResults(radiobuttonId)
                communicator.openSecondQuestion()
            }
            nextButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openFourthQuestion()
            }
            setPreviousResult()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveResults(id: Int?) {
        id?.let { preferences.edit()
            .putInt(PREF_THIRD_QUEST_ANSWER_ID, it)
            .putString(PREF_THIRD_QUEST_ANSWER_VALUE, thirdQuestion[it])
            .apply()
        }
    }

    private fun setPreviousResult() {
        if (preferences.contains(PREF_THIRD_QUEST_ANSWER_ID)) {
            (binding.radioGroup.getChildAt(
                preferences.getInt(
                    PREF_THIRD_QUEST_ANSWER_ID,
                    -1
                )
            ) as RadioButton).isChecked = true
        }
    }
}