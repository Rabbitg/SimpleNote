package com.example.simplenote.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplenote.Adapter.TodoAdapter
import com.example.simplenote.Model.Todo
import com.example.simplenote.R
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnDeleteListener  {

    public lateinit var adapter: TodoAdapter
    private lateinit var realm: Realm

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        //초기화
        Realm.init(this)

        // DB 마이그레이션 해주기 위해서 필히 해야하는 것
        val config = RealmConfiguration.Builder()
            .name("Version 1")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        //초기화 하기 위함
        realm = Realm.getInstance(config)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar!!.hide()

        setContentView(R.layout.activity_main)
        addNotes.setOnClickListener { addTodo() }

        val todos: ArrayList<Todo> = this.getTodos()
        this.adapter = TodoAdapter(todos)
        // Open the realm for the UI thread.


        todos_rv.layoutManager = LinearLayoutManager(this)
        todos_rv.adapter = this.adapter

    }

    private fun addTodo () {
        startActivity(Intent(this, AddTodo::class.java))
    }

    private fun getTodos (): ArrayList<Todo>{
        return ArrayList(this.realm.where(Todo::class.java).findAll())
    }

    override fun setOnDeleteListener() {
        this.adapter.items = getTodos()
        this.adapter.notifyDataSetChanged()
    }
}
