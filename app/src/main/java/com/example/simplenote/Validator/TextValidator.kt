package com.example.simplenote.Validator

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

open class TextValidator(textView: TextView) : TextWatcher {
    lateinit var textView : TextView

    init {
        this.textView = textView
    }

    open fun validate (textView: TextView, text: String) {}

    override fun afterTextChanged(s: Editable?) {
        val text = textView.text.toString()
        validate(textView, text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}