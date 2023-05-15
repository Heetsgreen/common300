package com.brainup3.common300

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.brainup3.common300.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setFrag(0)

        binding.btnFragment1.setOnClickListener {
            setFrag(0)
            binding.imgMain.setImageResource(R.drawable.icon_home_select)
            binding.imgCategory.setImageResource(R.drawable.icon_category)
            binding.imgDaily.setImageResource(R.drawable.icon_daily)
            binding.tvMain.setTextColor(Color.parseColor("#0070C0"))
            binding.tvCategory.setTextColor(Color.parseColor("#7F7F7F"))
            binding.tvDaily.setTextColor(Color.parseColor("#7F7F7F"))
        }
        binding.btnFragment2.setOnClickListener {
            setFrag(1)
            binding.imgMain.setImageResource(R.drawable.icon_home)
            binding.imgCategory.setImageResource(R.drawable.icon_category_select)
            binding.imgDaily.setImageResource(R.drawable.icon_daily)
            binding.tvMain.setTextColor(Color.parseColor("#7F7F7F"))
            binding.tvCategory.setTextColor(Color.parseColor("#0070C0"))
            binding.tvDaily.setTextColor(Color.parseColor("#7F7F7F"))
        }
        binding.btnFragment3.setOnClickListener {
            setFrag(2)
            binding.imgMain.setImageResource(R.drawable.icon_home)
            binding.imgCategory.setImageResource(R.drawable.icon_category)
            binding.imgDaily.setImageResource(R.drawable.icon_daily_select)
            binding.tvMain.setTextColor(Color.parseColor("#7F7F7F"))
            binding.tvCategory.setTextColor(Color.parseColor("#7F7F7F"))
            binding.tvDaily.setTextColor(Color.parseColor("#0070C0"))
        }


    }

    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame, Fragment_main()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, Fragment_category()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, Fragment_daily()).commit()
            }
        }

    }
}