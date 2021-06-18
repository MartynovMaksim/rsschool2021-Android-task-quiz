package com.rsschool.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.databinding.FragmentFirstQuestionBinding

private var _binding: FragmentFirstQuestionBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator
private var PREF_FIRST_QUEST_ANSWER = "PREF_FIRST_QUEST_ANSWER"

class FirstQuestion : Fragment() {

    private lateinit var preferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = activity as Communicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferences =
            requireNotNull(activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        _binding = FragmentFirstQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            previousButton.isEnabled = false
            question.text = "Какой город является столицей России?"
            optionOne.text = "Москва"
            optionTwo.text = "Санкт-Петербург"
            optionThree.text = "Самара"
            optionFour.text = "Казань"
            optionFive.text = "Мытищи"
            var radiobuttonId: Int? = null
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                radiobuttonId =
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId))
            }
            nextButton.setOnClickListener {
                radiobuttonId?.let { it1 ->
                    preferences.edit().putInt(
                        PREF_FIRST_QUEST_ANSWER,
                        it1
                    ).apply()
//                    communicator.openSecondQuestion(it1)
                    communicator.openSecondQuestion()
                }
//                radiobuttonId?.let { communicator.openSecondQuestion(it) }
            }
            val id = arguments?.getInt(PREVIOUS_RADIOBUTTON_ID)
//            if (id != -1) {
//                id?.let {
//                    (radioGroup.getChildAt(it) as RadioButton).isChecked = true
//                }
//            }
            if (preferences.contains(PREF_FIRST_QUEST_ANSWER)) {
                (radioGroup.getChildAt(preferences.getInt(PREF_FIRST_QUEST_ANSWER,-1)) as RadioButton).isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(radioButtonId: Int): FirstQuestion {
            val fragment = FirstQuestion()
            val args = Bundle()
            args.putInt(PREVIOUS_RADIOBUTTON_ID, radioButtonId)
            fragment.arguments = args
            return fragment

        }

        private const val PREVIOUS_RADIOBUTTON_ID = "RADIOBUTTON_ID"
    }

}