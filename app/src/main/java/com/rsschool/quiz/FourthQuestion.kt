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
import com.rsschool.quiz.databinding.FragmentFourthQuestionBinding

private var _binding: FragmentFourthQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator
class FourthQuestion : Fragment() {

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
        activity?.window?.statusBarColor = requireNotNull(context?.getColor(R.color.cyan_100_dark))
        preferences =
            requireNotNull(activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        _binding = FragmentFourthQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nextButton.isEnabled = false
            question.text = FOURTH_QUESTION
            optionOne.text = fourthQuestion[0]
            optionTwo.text = fourthQuestion[1]
            optionThree.text = fourthQuestion[2]
            optionFour.text = fourthQuestion[3]
            optionFive.text = fourthQuestion[4]

            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
                nextButton.isEnabled = true
            }
            previousButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openThirdQuestion()
            }
            toolbar.setNavigationOnClickListener {
                saveResults(radiobuttonId)
                communicator.openThirdQuestion()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                saveResults(radiobuttonId)
                communicator.openThirdQuestion()
            }
            nextButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openFifthQuestion()
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
            .putInt(PREF_FOURTH_QUEST_ANSWER_ID, it)
            .putString(PREF_FOURTH_QUEST_ANSWER_VALUE, fourthQuestion[it])
            .apply()
        }
    }

    private fun setPreviousResult() {
        if (preferences.contains(PREF_FOURTH_QUEST_ANSWER_ID)) {
            (binding.radioGroup.getChildAt(
                preferences.getInt(
                    PREF_FOURTH_QUEST_ANSWER_ID,
                    -1
                )
            ) as RadioButton).isChecked = true
        }
    }


}