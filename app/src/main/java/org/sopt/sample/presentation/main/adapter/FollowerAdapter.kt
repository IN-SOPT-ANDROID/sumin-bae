package org.sopt.sample.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.sopt.sample.data.model.response.ResponseFollowerListDto
import org.sopt.sample.databinding.ItemFollowerBinding
import org.sopt.sample.databinding.ItemFollowerHeaderBinding

class FollowerAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var followerList: List<ResponseFollowerListDto.Follower> = emptyList()

    class FollowerViewHolder(
        private val binding: ItemFollowerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseFollowerListDto.Follower) {
            binding.tvFollowerName.text = data.firstName
            binding.tvFollowerEmail.text = data.email
            binding.ivFollower.load(data.avatar)
        }
    }

    class HeaderViewHolder(
        private val binding: ItemFollowerHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = ItemFollowerHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            TYPE_FOLLOWER -> {
                val binding = ItemFollowerBinding.inflate(inflater, parent, false)
                FollowerViewHolder(binding)
            }
            else -> throw IllegalArgumentException("view type not found")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FollowerViewHolder) holder.onBind(followerList[position - 1])
    }

    override fun getItemCount() = followerList.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_FOLLOWER
        }
    }

    fun setFollowerList(list: List<ResponseFollowerListDto.Follower>) {
        this.followerList = list.toList()
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOLLOWER = 1
    }
}