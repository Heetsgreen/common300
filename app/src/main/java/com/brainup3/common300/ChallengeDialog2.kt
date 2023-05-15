package com.brainup3.common300

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.brainup3.common300.databinding.DialogChallengeQuit2Binding
import com.google.android.gms.ads.MobileAds
import java.text.DecimalFormat

class ChallengeDialog2: DialogFragment() {
    private var _binding: DialogChallengeQuit2Binding? = null
    private val binding get() = _binding!!
    private var scoreChallengeHigh: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogChallengeQuit2Binding.inflate(inflater, container, false)
        val view = binding.root

        changeHighScore()

        binding.btnToHome.setOnClickListener {
            buttonClickListener.onButtonToHomeClicked()
            dismiss()
        }

        return view

    }

    private fun changeHighScore() {
        val dec_up = DecimalFormat("#,###")
        val scoreChallenge = arguments?.getInt("money")
        val scoreChallengeHigh = arguments?.getInt("HighScore")
        binding.tvScoreNow4.setText("${dec_up.format(scoreChallenge)}")
        binding.tvScoreNow3.setText("${dec_up.format(scoreChallengeHigh)}")
    }

    override fun onResume() {
        super.onResume()
        val windowManager = getContext()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        size.x
        size.y

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 1).toInt()
        params?.height = (deviceHeight * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    interface onButtonClickListener {
        fun onButtonToHomeClicked()
    }

    fun setButtonClickListener(buttonClickListener: onButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    private lateinit var buttonClickListener: onButtonClickListener
}