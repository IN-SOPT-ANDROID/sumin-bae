package org.sopt.sample.presentation.main.follower

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.response.ResponseFollowerListDto

class FollowerViewModel : ViewModel() {
    val followerList = mutableListOf<ResponseFollowerListDto.Follower>()
}