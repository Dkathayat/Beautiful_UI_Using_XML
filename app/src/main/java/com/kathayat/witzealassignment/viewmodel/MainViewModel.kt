package com.kathayat.witzealassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kathayat.witzealassignment.R
import com.kathayat.witzealassignment.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _userListData: MutableLiveData<ArrayList<UserData>> = MutableLiveData()
    private val _winner:MutableLiveData<Int> = MutableLiveData()
    val winner get() = _winner
    val userListData = ArrayList<UserData>()
    val randUserImg = listOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
    val randomImg = randUserImg.shuffled()
    val names =
        listOf("Deepak Singh kathayat", "Ram Kumar", "Frank", "Bob David", "Sneha kumari", "Rohit Yadav")

    init {
        getWinner(36)
    }

    fun setUserData(): LiveData<ArrayList<UserData>> {
        for (i in 1..1000) {
            val randomPrice = Random.nextInt(100, 1001)
            val randomName = names.random()
            val data = UserData(i, randomName, randomImg[i % randomImg.size], randomPrice)
            userListData.add(data)
            _userListData.value = userListData
        }
        return _userListData
    }
    //we can get the winner from remote data source using repository but for simplicity just setting in in the init block
    private fun getWinner(value:Int){
        _winner.value = value
    }
}
