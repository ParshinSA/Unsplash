package com.example.unsplash.data.storages

interface AuthStateStorage {

    fun isAuthUser(): Boolean
    fun changeAuthStateUser(state: Boolean)
}
