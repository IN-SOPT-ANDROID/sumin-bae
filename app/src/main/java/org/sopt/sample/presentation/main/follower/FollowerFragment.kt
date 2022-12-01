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
import org.sopt.sample.R
import org.sopt.sample.data.model.response.ResponseFollowerListDto
import org.sopt.sample.data.service.ServicePool
import org.sopt.sample.databinding.FragmentFollowerBinding
import org.sopt.sample.presentation.main.adapter.FollowerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    private val viewModel by viewModels<FollowerViewModel>()
    private val followerService = ServicePool.followerService
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
        followerService.getUserList()
            .enqueue(object : Callback<ResponseFollowerListDto> {
                override fun onResponse(
                    call: Call<ResponseFollowerListDto>,
                    response: Response<ResponseFollowerListDto>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { viewModel.followerList.addAll(it.data) }

                        val adapter = FollowerAdapter(requireContext())
                        binding.rvFollowers.adapter = adapter
                        adapter.setFollowerList(viewModel.followerList)

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
                    } else {
                        Snackbar.make(binding.root, R.string.follower_fail, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseFollowerListDto>, t: Throwable) {
                    Snackbar.make(binding.root,
                        R.string.follower_network_fail,
                        Snackbar.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}