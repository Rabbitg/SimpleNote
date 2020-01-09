package com.example.simplenote.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.simplenote.Model.Todo
import com.example.simplenote.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit_todo.*

class EditTodoActivity: AppCompatActivity() {
    val realm by lazy { Realm.getDefaultInstance() }
    val id by lazy { intent.getStringExtra("id") }
    lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_edit_todo)

        if (supportActionBar != null) {
            supportActionBar!!.title = "Edit Todo"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        todo = this.realm.where(Todo::class.java)
            .equalTo("id", this.id)
            .findFirst()!!

        titleInput.setText(todo.title.toString())
        descriptionInput.setText(todo.description.toString())


        submitButton.setOnClickListener {
            this.udateTodo()
            Toast.makeText(this, "수정 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun udateTodo () {
        this.realm.executeTransaction {
            this.todo.title = titleInput.text.toString()
            this.todo.description = descriptionInput.text.toString()
        }
    }
}