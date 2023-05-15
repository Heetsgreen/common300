package com.brainup3.common300

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.brainup3.common300.databinding.ActivityExplainBinding

class ExplainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExplainBinding

    private var mCurrentExplain: Int = 0
    private var mExplain300List: ArrayList<Explain300>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityExplainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dm = applicationContext.resources.displayMetrics
        val width = (dm.widthPixels * 1).toInt() // Display 사이즈의 90%
        val height = (dm.heightPixels * 0.5).toInt() // Display 사이즈의 90%
        window.attributes.width = width
        window.attributes.height = height

        mExplain300List = Constant_Explain.getExplains()

        getExplainExtra()

        setExplain()

        binding.btnOkay.setOnClickListener {
            finish()
        }
    }

    private fun setExplain() {
        val explain = mExplain300List!![mCurrentExplain-1]

        binding.tvMainExplain.text = explain.explain
    }

    private fun getExplainExtra() {
        if (intent.hasExtra("Explain")) {
            val explainExtra = intent.getIntExtra("Explain", 1)
            mCurrentExplain += explainExtra
        }
    }
}