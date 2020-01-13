package com.example.simplenote.Activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.simplenote.Model.Todo
import com.example.simplenote.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.android.synthetic.main.activity_edit_todo.*
import kotlinx.android.synthetic.main.activity_edit_todo.dateTextView
import kotlinx.android.synthetic.main.activity_edit_todo.descriptionInput
import kotlinx.android.synthetic.main.activity_edit_todo.dp_Button
import kotlinx.android.synthetic.main.activity_edit_todo.submitButton
import kotlinx.android.synthetic.main.activity_edit_todo.titleInput
import java.util.*

class EditTodoActivity: AppCompatActivity() {
    val realm by lazy { Realm.getDefaultInstance() }
    val id by lazy { intent.getStringExtra("id") }
    //캘린더 객체 생성 edit
    val calendar = Calendar.getInstance()

    lateinit var todo: Todo

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_edit_todo)

        if (supportActionBar != null) {
            supportActionBar!!.title = "수정 페이지"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        todo = this.realm.where(Todo::class.java)
            .equalTo("id", this.id)
            .findFirst()!!

        titleInput.setText(todo.title.toString())
        descriptionInput.setText(todo.description.toString())
        dateTextView.setText(todo.dateText.toString())


        submitButton.setOnClickListener {
            if (this.formValidated()){
                this.udateTodo()
            Toast.makeText(this, "수정 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
            else{
                Toast.makeText(this, "제목과 내용 입력을 모두 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        dp_Button.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, day->
                dateTextView.text = "${year}년 ${month+1}월 ${day}일"
            },year,month,day)
            dpd.show()
        }

    }

    private fun udateTodo () {
        this.realm.executeTransaction {
            this.todo.title = titleInput.text.toString()
            this.todo.description = descriptionInput.text.toString()
            this.todo.dateText = dateTextView.text.toString()
        }
    }
    fun formValidated (): Boolean {
        if (titleInput.text.toString() == "") {
            return false
        }
        if (descriptionInput.text.toString() == "") {
            return false
        }
        return true
    }

}