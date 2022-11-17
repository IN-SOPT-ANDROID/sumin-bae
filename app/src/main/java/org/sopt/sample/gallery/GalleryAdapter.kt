package org.sopt.sample.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.GalleryItemBinding

class GalleryAdapter(
    initItems: List<Int> = listOf(),
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private var items: List<Int> = initItems

    fun setItems(items: List<Int>) {
        this.items = items
        notifyDataSetChanged()
    }

    class GalleryViewHolder(
        private val binding: GalleryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(src: Int) {
            binding.imgGallery.setImageResource(src)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}