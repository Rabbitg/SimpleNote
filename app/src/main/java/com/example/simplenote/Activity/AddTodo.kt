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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_list_item.*
import java.util.*


class AddTodo : AppCompatActivity() {

    private lateinit var realm: Realm //Realm 초기화
    val todo: Todo? = null
    val calendar:Calendar = Calendar.getInstance() // 캘린더 객체

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Realm 초기화
    Realm.init(this)
    realm = Realm.getDefaultInstance()

    window.requestFeature(Window.FEATURE_ACTION_BAR)
    setContentView(R.layout.activity_add_todo)

    if(supportActionBar != null){
        supportActionBar!!.title = getString(R.string.add_todo_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


    submitButton.setOnClickListener{
        if(this.formValidated()){
            this.addTodo()
            Toast.makeText(this, "성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(this, "제목과 내용 입력을 모두 해주세요.", Toast.LENGTH_SHORT).show()
        }
    }


        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

     dp_Button.setOnClickListener {
         val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,year,month,day->
             dateTextView.text = "${year}년 ${month+1}월 ${day}일"
         },year,month,day)
            dpd.show()
     }

}

fun addTodo() {
    this.realm.executeTransaction{
        val todo = this.realm.createObject(Todo::class.java, UUID.randomUUID().toString())
        todo.title = titleInput.text.toString()
        todo.description = descriptionInput.text.toString()
        // 캘린더 타임스탬프
        todo.dateText = dateTextView.text.toString()

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