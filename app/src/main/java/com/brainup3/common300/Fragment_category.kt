package com.brainup3.common300

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brainup3.common300.databinding.FragmentCategory2Binding
import com.brainup3.common300.databinding.FragmentCategoryBinding

class Fragment_category : Fragment() {
    private var mBinding : FragmentCategory2Binding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCategory2Binding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHistory.setOnClickListener {
            val intent = Intent(getActivity(), QuizHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnLiterature.setOnClickListener {
            val intent = Intent(getActivity(), QuizLiteratureActivity::class.java)
            startActivity(intent)
        }

        binding.btnNews.setOnClickListener {
            val intent = Intent(getActivity(), QuizNewsActivity::class.java)
            startActivity(intent)
        }

        binding.btnLanguage.setOnClickListener {
            val intent = Intent(getActivity(), QuizLanguageActivity::class.java)
            startActivity(intent)
        }

        binding.btnSports.setOnClickListener {
            val intent = Intent(getActivity(), QuizSportsActivity::class.java)
            startActivity(intent)
        }

        binding.btnWorld.setOnClickListener {
            val intent = Intent(getActivity(), QuizWorldActivity::class.java)
            startActivity(intent)
        }

        binding.btnPop.setOnClickListener {
            val intent = Intent(getActivity(), QuizMassActivity::class.java)
            startActivity(intent)
        }

        binding.btnScience.setOnClickListener {
            val intent = Intent(getActivity(), QuizScienceActivity::class.java)
            startActivity(intent)
        }


    }
}
