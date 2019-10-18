package com.example.a2unidadeprova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnDeleteListener, OnEditListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val database: SQLiteRepository = SQLiteRepository(this)
    private lateinit var listtasks: ArrayList<Task>


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
    override fun deleteTask(index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editTask(position: Int, name: String, description: String, checked: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database.save(Task("Tarefa 1", "descricao qualquer", 1))
        listtasks = database.selectAll();
        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskListAdapter(listtasks, this, this, this)

        recyclerView = findViewById<RecyclerView>(R.id.task_list_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

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
