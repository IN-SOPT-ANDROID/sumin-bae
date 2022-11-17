package org.sopt.sample.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ColorItemBinding
import org.sopt.sample.databinding.HeaderItemBinding
import org.sopt.sample.data.local.Color

class FollowerAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var colorList: List<Color> = emptyList()

    class ColorViewHolder(
        private val binding: ColorItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Color) {
            binding.tvBlueName.text = data.name
            binding.tvBlueHex.text = data.hex
            binding.ivBlue.setBackgroundColor(android.graphics.Color.parseColor(data.hex))
        }
    }

    class HeaderViewHolder(
        private val binding: HeaderItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Color.TYPE_HEADER -> {
                val binding = HeaderItemBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            Color.TYPE_COLOR -> {
                val binding = ColorItemBinding.inflate(inflater, parent, false)
                ColorViewHolder(binding)
            }
            else -> throw IllegalArgumentException("view type not found")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ColorViewHolder) holder.onBind(colorList[position - 1])
    }

    override fun getItemCount() = colorList.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> Color.TYPE_HEADER
            else -> Color.TYPE_COLOR
        }
    }

    fun setColorList(list: List<Color>) {
        this.colorList = list.toList()
        notifyDataSetChanged()
    }
}