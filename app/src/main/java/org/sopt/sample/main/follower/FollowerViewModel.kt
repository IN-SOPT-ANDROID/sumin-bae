package org.sopt.sample.main.follower

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.ResponseFollowerListDto

class FollowerViewModel : ViewModel() {
    val followerList = mutableListOf<ResponseFollowerListDto.Follower>()
}