package org.sopt.sample.main.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.sopt.sample.databinding.FragmentFollowerBinding
import org.sopt.sample.main.adapter.FollowerAdapter

class FollowerFragment : Fragment() {
    private val viewModel by viewModels<FollowerViewModel>()
    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FollowerAdapter(requireContext())
        binding.rvColors.adapter = adapter
        adapter.setColorList(viewModel.blueColorList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}