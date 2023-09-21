package com.kathayat.witzealassignment.utils

import com.kathayat.witzealassignment.model.UserData

interface FocusedUser {
    fun getUser(userData: UserData)
    fun isActice(boolean: Boolean)
}