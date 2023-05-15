package com.brainup3.common300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.brainup3.common300.databinding.PopExplainBinding

class ExplainDialog: DialogFragment() {
    private var _binding: PopExplainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PopExplainBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
}