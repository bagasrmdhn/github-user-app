package com.dicoding.mysecondsubmission

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dicoding.mysecondsubmission.databinding.ItemRowUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val list = ArrayList<User>()

    private var onItemClick: OnItemClick? = null

    fun setOnItemClick(onItemClick: OnItemClick){
        this.onItemClick = onItemClick
    }

    fun setList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClick{
        fun onItemClicked(data: User)
    }

    inner class ViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User) {
            binding.apply {
                tvItemName.text = user.login
                tvItemDetail.text = user.id.toString()
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transform(CircleCrop())
                    .into(imgItemPhoto)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClick?.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int = list.size
}