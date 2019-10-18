package com.example.a2unidadeprova

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_task.*

class RegisterTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_task)

        btnregistertask.setOnClickListener {
            val it = Intent()
            val task_info: Array<String> = arrayOf(registertaskname.text.toString(), registertaskdescription.text.toString())
            it.putExtra("newtask", task_info)
            setResult(Activity.RESULT_OK, it)
            finish()
        }
    }
}
