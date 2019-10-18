package com.example.a2unidadeprova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.btnsync){
        }
        if(item.itemId == R.id.btnregistertask){
        }
        if(item.itemId == R.id.btnfinishtask){
        }
        if(item.itemId == R.id.btnlogout){
            logout()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun logout(){
        val logged = getSharedPreferences("logged", Context.MODE_PRIVATE)
        val editor = logged.edit()
        editor.clear()
        editor.commit()
        val it = Intent(this, LoginActivity::class.java)
        startActivity(it)
    }
}
