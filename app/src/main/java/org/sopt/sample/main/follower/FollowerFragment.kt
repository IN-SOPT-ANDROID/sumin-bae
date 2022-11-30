package org.sopt.sample.main.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.data.remote.ResponseFollowerListDto
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.FragmentFollowerBinding
import org.sopt.sample.main.adapter.FollowerAdapter
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
        for (i in 1..2) {
            showFollower(i)
        }
    }

    private fun showFollower(page: Int) {
        followerService.getUserList(page)
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
                    }
                    else {
                        Snackbar.make(binding.root, R.string.follower_fail, Snackbar.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseFollowerListDto>, t: Throwable) {
                    Snackbar.make(binding.root, R.string.follower_fail, Snackbar.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}