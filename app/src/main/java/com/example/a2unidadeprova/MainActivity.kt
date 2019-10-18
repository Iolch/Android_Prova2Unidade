package com.example.a2unidadeprova

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnDeleteListener, OnEditListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val database: SQLiteRepository = SQLiteRepository(this)
    private lateinit var listtasks: ArrayList<Task>
    private val REGISTER:Int = 1;


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.btnsync){
            refresh()
        }
        if(item.itemId == R.id.btnregistertask){
            register()
        }
        if(item.itemId == R.id.btnfinishtask){
        }
        if(item.itemId == R.id.btnlogout){
            logout()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun deleteTask(index: Int) {
        DeleteDialogFragment.show(supportFragmentManager, object : DeleteDialogFragment.OnDialoListener {
            override fun onOK() {
                database.remove(listtasks[index])
                listtasks.removeAt(index)
                viewAdapter.notifyDataSetChanged()
            }
        })

    }

    override fun editTask(position: Int, name: String, description: String, checked: Int) {
        val edttask:Task = Task(listtasks[position].name, description, checked, listtasks[position].id)
        listtasks[position] = edttask
        database.save(edttask)
        viewAdapter.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listtasks = database.selectAll();
        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskListAdapter(listtasks, this, this, this)

        recyclerView = findViewById<RecyclerView>(R.id.task_list_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode === Activity.RESULT_OK){
            if(requestCode === REGISTER){
                val newtask = data!!.getStringArrayExtra("newtask")
                store(newtask[0], newtask[1])
            }
        }else{}
    }
    private fun store(name:String, description: String){
        listtasks.add(Task(name, description, 0))
        val newposition = listtasks.size - 1;
        database.save(listtasks[newposition])
        viewAdapter.notifyItemInserted(newposition)
        viewAdapter.notifyDataSetChanged()
        //CHAMA A NOTIFICATION
        NotificationUtils.notificationSimple(this, name, description, 1)
    }

    fun logout(){
        val logged = getSharedPreferences("logged", Context.MODE_PRIVATE)
        val editor = logged.edit()
        editor.clear()
        editor.commit()
        val it = Intent(this, LoginActivity::class.java)
        startActivity(it)
    }
    fun register(){
        val it = Intent(this, RegisterTaskActivity::class.java)
        startActivityForResult(it, REGISTER)
    }
    fun refresh(){
        listtasks = database.selectAll()
        Toast.makeText(this,"Atualizando!", Toast.LENGTH_SHORT).show()
    }
}
