package org.sopt.sample.presentation.main.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.FragmentFollowerBinding
import org.sopt.sample.presentation.main.adapter.FollowerAdapter

class FollowerFragment : Fragment() {
    private val viewModel by viewModels<FollowerViewModel>()
    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserList()

        val adapter = FollowerAdapter(requireContext())
        binding.rvFollowers.adapter = adapter

        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    0 -> 2
                    else -> 1
                }
            }

        }
        binding.rvFollowers.layoutManager = gridLayoutManager

        viewModel.followerList.observe(viewLifecycleOwner) {
            adapter.setFollowerList(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}