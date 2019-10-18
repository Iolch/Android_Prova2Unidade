package com.example.a2unidadeprova

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        val users = getSharedPreferences("users", Context.MODE_PRIVATE);

        btnregister.setOnClickListener(){
            val user = users.getStringSet(registeremail.text.toString(), null);
            if(user == null){
                val it = Intent()
                val editor = users.edit()
                val user_info: Array<String> = arrayOf(registeremail.text.toString(), registername.text.toString(), registerpassword.text.toString())
                it.putExtra("newuser", user_info)
                editor.putStringSet(user_info.get(0), setOf(user_info.get(1), user_info.get(2)))
                editor.commit()
                setResult(Activity.RESULT_OK, it)
                finish()
            }else{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
//            val it = Intent()
//            var position = pageintent.getIntExtra("note_position", 1)
//            it.putExtra("text", notetext.text.toString())
//            it.putExtra("note_position", position)
//            setResult(Activity.RESULT_OK, it)
//            finish()
        }
    }
}
