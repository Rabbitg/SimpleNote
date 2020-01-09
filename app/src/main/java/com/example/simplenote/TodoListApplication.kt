package com.example.simplenote

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class TodoListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("todos.db")
            .build()

        Realm.setDefaultConfiguration(config)
    }
}