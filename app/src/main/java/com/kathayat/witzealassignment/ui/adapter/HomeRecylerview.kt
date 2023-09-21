package com.kathayat.witzealassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kathayat.witzealassignment.databinding.RecylerItemBinding
import com.kathayat.witzealassignment.model.UserData
import com.kathayat.witzealassignment.utils.FocusedUser

class HomeRecylerview(
    private val userData: ArrayList<UserData>,
    private val user: FocusedUser
) : RecyclerView.Adapter<HomeRecylerview.ViewHolder>() {

    private var isAnimationShown = false
    var winner = -1

    fun addData(newData: List<UserData>) {
        userData.addAll(newData)
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecylerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(userData[position])
        }
        if (position == winner && !isAnimationShown) {
            user.getUser(userData[position])
            user.isActice(true)
            isAnimationShown = true
        } else if (position < winner && isAnimationShown) {
            user.isActice(false)
            isAnimationShown = false
        }
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    class ViewHolder(val binding: RecylerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            binding.userID.text = userData.userId.toString()
            binding.userName.text = userData.userName
            binding.profileImg.setImageResource(userData.userImg)
            binding.userPrice.text = userData.price.toString()
        }
    }
}


