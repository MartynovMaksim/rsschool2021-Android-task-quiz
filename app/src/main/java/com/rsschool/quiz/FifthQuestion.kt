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
import com.rsschool.quiz.databinding.FragmentFifthQuestionBinding

private var _binding: FragmentFifthQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator
class FifthQuestion : Fragment() {

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
        activity?.window?.statusBarColor = requireNotNull(context?.getColor(R.color.deep_purple_100_dark))
        preferences =
            requireNotNull(activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        _binding = FragmentFifthQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nextButton.isEnabled = false
            question.text = FIFTH_QUESTION
            optionOne.text = fifthQuestion[0]
            optionTwo.text = fifthQuestion[1]
            optionThree.text = fifthQuestion[2]
            optionFour.text = fifthQuestion[3]
            optionFive.text = fifthQuestion[4]

            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
                nextButton.isEnabled = true
            }
            previousButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.openFourthQuestion()
            }
            toolbar.setNavigationOnClickListener {
                saveResults(radiobuttonId)
                communicator.openFourthQuestion()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                saveResults(radiobuttonId)
                communicator.openFourthQuestion()
            }
            nextButton.setOnClickListener {
                saveResults(radiobuttonId)
                communicator.scoreFragment()
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
            .putInt(PREF_FIFTH_QUEST_ANSWER_ID, it)
            .putString(PREF_FIFTH_QUEST_ANSWER_VALUE, fifthQuestion[it])
            .apply()
        }
    }

    private fun setPreviousResult() {
        if (preferences.contains(PREF_FIFTH_QUEST_ANSWER_ID)) {
            (binding.radioGroup.getChildAt(
                preferences.getInt(
                    PREF_FIFTH_QUEST_ANSWER_ID,
                    -1
                )
            ) as RadioButton).isChecked = true
        }
    }

}