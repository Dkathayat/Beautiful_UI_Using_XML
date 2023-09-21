package com.kathayat.witzealassignment.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kathayat.witzealassignment.R
import com.kathayat.witzealassignment.databinding.ActivityMainBinding
import com.kathayat.witzealassignment.model.UserData
import com.kathayat.witzealassignment.ui.adapter.HomeRecylerview
import com.kathayat.witzealassignment.utils.FocusedUser
import com.kathayat.witzealassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FocusedUser {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val userListData = ArrayList<UserData>()
    private var currentPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.setUserData().observe(this) {
            it.onEach { userData ->
                userListData.add(userData)
            }
        }
        val recyclerView: RecyclerView = findViewById(R.id.mainRecylerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HomeRecylerview(userListData,this)
        recyclerView.adapter = adapter

        mainViewModel.winner.observe(this) {
            adapter.winner = it
        }
        /* In real scenario its good to implement paging library but for the simplicity just adding a normal pagination */
        binding.mainlayout.mainRecylerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) { loadNextPage(adapter) }
            }
        })
    }

    private fun loadNextPage(adapter: HomeRecylerview) {
        val itemsPerPage = 10
        val startIndex = currentPage * itemsPerPage
        val endIndex = (currentPage + 1) * itemsPerPage
        val nextPageData = userListData.subList(startIndex, endIndex)
        adapter.addData(nextPageData)

        currentPage++
    }

    override fun getUser(userData: UserData) {
        val focusedUser = binding.mainlayout.focusedUser
        focusedUser.userName.text = userData.userName
        focusedUser.userID.text = userData.userId.toString()
        focusedUser.profileImg.setImageResource(userData.userImg)
    }

    override fun isActice(boolean: Boolean) {
        val focusedVisibilty: View = findViewById(R.id.focusedUser)
        if (boolean){
            focusedVisibilty.visibility = View.VISIBLE
            animateView(focusedVisibilty,0)
        } else {
            focusedVisibilty.visibility = View.GONE
            animateView(focusedVisibilty,1)

        }
    }

    private fun animateView(view: View,animationType:Int){
        val animation: TranslateAnimation = if (animationType == 0) {
            TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 5f,
                Animation.RELATIVE_TO_SELF, 0f
            )
        } else {
            TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 5f
            )
        }
        animation.duration =700
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                animation.repeatMode
            }
            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        view.startAnimation(animation)
    }
}
