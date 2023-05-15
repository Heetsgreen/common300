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
import com.brainup3.common300.databinding.DialogNolifeBinding
import com.google.android.gms.ads.MobileAds

class NoLifeDialog: DialogFragment() {
    private var _binding: DialogNolifeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogNolifeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnGetLife.setOnClickListener {
            buttonClickListener.onButtonGetLifeClicked()
            dismiss()
        }

        binding.btnToHome.setOnClickListener {
            buttonClickListener.onButtonToHomeClicked()
            dismiss()
        }

        return view

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
        fun onButtonGetLifeClicked()
        fun onButtonToHomeClicked()
    }

    fun setButtonClickListener(buttonClickListener: onButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    private lateinit var buttonClickListener: onButtonClickListener
}