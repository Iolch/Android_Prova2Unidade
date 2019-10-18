package com.example.a2unidadeprova

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val REGISTER: Int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val users = getSharedPreferences("users", Context.MODE_PRIVATE);
        val logged = getSharedPreferences("logged", Context.MODE_PRIVATE);

        btnlogin.setOnClickListener(){
            val user = users.getStringSet(loginemail.text.toString(), null)
            if(user != null){
                if( user.contains(loginpassword.text.toString())){
                    val editor = logged.edit()
                    editor.clear()
                    editor.putStringSet("user", user)
                    editor.commit()
                    //redirect to list
                }else {
                    Toast.makeText(this,"Senha incorreta!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Usuário não cadastrado!", Toast.LENGTH_LONG).show()
            }
        }
        btnnew.setOnClickListener(){
            val it = Intent(this, RegisterUserActivity::class.java)
            startActivityForResult(it, REGISTER)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == REGISTER){
                if(resultCode == Activity.RESULT_OK){
                    val newuser = data!!.getStringArrayExtra("newuser")
                    loginemail.setText(newuser.get(0))
                    loginpassword.setText(newuser.get(2))
                }else{
                    Toast.makeText(this,"Algo deu errado com o cadastro.", Toast.LENGTH_LONG).show()
                }

        }
    }
}
