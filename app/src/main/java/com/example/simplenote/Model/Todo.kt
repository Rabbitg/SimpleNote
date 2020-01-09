package com.example.simplenote.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo : RealmObject() {
    @PrimaryKey
    var id:String? = null
    var title:String? = null
    var description: String? = null
    var date:Long = 0
}