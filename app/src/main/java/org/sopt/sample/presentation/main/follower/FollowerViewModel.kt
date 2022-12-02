package org.sopt.sample.presentation.main.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.response.ResponseFollowerListDto
import org.sopt.sample.data.service.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _followerList = MutableLiveData<List<ResponseFollowerListDto.Follower>>()
    val followerList: LiveData<List<ResponseFollowerListDto.Follower>>
        get() = _followerList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val followerService = ServicePool.followerService

    fun getUserList() {
        followerService.getUserList()
            .enqueue(object : Callback<ResponseFollowerListDto> {
                override fun onResponse(
                    call: Call<ResponseFollowerListDto>,
                    response: Response<ResponseFollowerListDto>,
                ) {
                    if (response.isSuccessful) {
                        _followerList.value = response.body()?.data
                    } else {
                        _errorMessage.value = "팔로워 목록을 불러오는데 실패했습니다"
                    }
                }

                override fun onFailure(call: Call<ResponseFollowerListDto>, t: Throwable) {
                    _errorMessage.value = "네트워크 연결에 문제가 있습니다"
                }

            })
    }
}