package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.rsschool.quiz.databinding.FragmentScoreBinding
import kotlin.system.exitProcess

private var _binding: FragmentScoreBinding? = null
private val binding get() = requireNotNull(_binding)
private lateinit var communicator: Communicator

class Score : Fragment() {

    private lateinit var preferences: SharedPreferences
    private var score: Int = 0
    private var text = ""

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
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        score = setScore()
        text = setText(score)
        with(binding) {
            scoreTextView.text = getString(R.string.score, score.toString())
            shareImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_SUBJECT,"Quiz results")
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                if (activity?.packageManager?.let {intent.resolveActivity(it)} != null) {
                    startActivity(intent)
                }
            }
            backImageView.setOnClickListener {
                communicator.openFifthQuestion()
            }
            exitImageView.setOnClickListener {
                activity?.finish()
                exitProcess(0)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            communicator.openFifthQuestion()
        }
    }

    private fun setScore(): Int {
        var score = 0
        if (preferences.getInt(PREF_FIRST_QUEST_ANSWER_ID,-1) == 0) score++
        if (preferences.getInt(PREF_SECOND_QUEST_ANSWER_ID,-1) == 4) score++
        if (preferences.getInt(PREF_THIRD_QUEST_ANSWER_ID,-1) == 3) score++
        if (preferences.getInt(PREF_FOURTH_QUEST_ANSWER_ID,-1) == 3) score++
        if (preferences.getInt(PREF_FIFTH_QUEST_ANSWER_ID,-1) == 2) score++
        return score
    }

    private fun setText(score: Int) : String {
        val sb = StringBuilder()
            .append("Your result: $score/5\n\n")
            .append("1) $FIRST_QUESTION\n")
            .append("Your answer: ${preferences.getString(PREF_FIRST_QUEST_ANSWER_VALUE,"")}\n")
            .append("2) $SECOND_QUESTION\n")
            .append("Your answer: ${preferences.getString(PREF_SECOND_QUEST_ANSWER_VALUE,"")}\n")
            .append("3) $THIRD_QUESTION\n")
            .append("Your answer: ${preferences.getString(PREF_THIRD_QUEST_ANSWER_VALUE,"")}\n")
            .append("4) $FOURTH_QUESTION\n")
            .append("Your answer: ${preferences.getString(PREF_FOURTH_QUEST_ANSWER_VALUE,"")}\n")
            .append("5) $FIFTH_QUESTION\n")
            .append("Your answer: ${preferences.getString(PREF_FIFTH_QUEST_ANSWER_VALUE,"")}\n")
        return sb.toString()
    }
}