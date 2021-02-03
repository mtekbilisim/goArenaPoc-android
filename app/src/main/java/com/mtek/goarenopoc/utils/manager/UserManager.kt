package com.mtek.goarenopoc.utils.manager

import com.mtek.goarenopoc.data.model.UserModelWithAuth

class UserManager {
    var user: UserModelWithAuth? = UserModelWithAuth()

    @JvmName("setUser1")
    fun setUser(newUser: UserModelWithAuth?) {
        user = newUser!!
    }

    fun clear() {
        user = null
    }

    companion object {
        val instance = UserManager()
    }
}