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
import androidx.annotation.RequiresApi
import com.rsschool.quiz.databinding.FragmentFirstQuestionBinding

private var _binding: FragmentFirstQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator


class FirstQuestion : Fragment() {

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
        activity?.window?.statusBarColor = requireNotNull(context?.getColor(R.color.deep_orange_100_dark))
        preferences =
            requireNotNull(activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        _binding = FragmentFirstQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            previousButton.isEnabled = false
            nextButton.isEnabled = false
            question.text = FIRST_QUESTION
            optionOne.text = firstQuestion[0]
            optionTwo.text = firstQuestion[1]
            optionThree.text = firstQuestion[2]
            optionFour.text = firstQuestion[3]
            optionFive.text = firstQuestion[4]
            toolbar.hideOverflowMenu()
            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
                nextButton.isEnabled = true
            }
            nextButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openSecondQuestion()
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
            .putInt(PREF_FIRST_QUEST_ANSWER_ID, it)
            .putString(PREF_FIRST_QUEST_ANSWER_VALUE, firstQuestion[it])
            .apply()
             }
    }

    private fun setPreviousResult() {
        if (preferences.contains(PREF_FIRST_QUEST_ANSWER_ID)) {
            (binding.radioGroup.getChildAt(
                preferences.getInt(
                    PREF_FIRST_QUEST_ANSWER_ID,
                    -1
                )
            ) as RadioButton).isChecked = true
        }
    }

}