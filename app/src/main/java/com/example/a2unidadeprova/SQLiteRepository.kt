package com.example.a2unidadeprova

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class SQLiteRepository(context: Context): TaskRepository {
    private val helper: TaskSqlHelper = TaskSqlHelper(context)
    override fun save(task: Task) {
        if(task.id == 0L)
        {
//            return insert(note)
            insert(task)
        }else{
            update(task)
//            return update(note)
        }
    }

    override fun remove(vararg tasks: Task) {
        val db = helper.writableDatabase
        for(task in tasks){
            db.delete(TABLE_NAME, "$COLOUMN_ID = ?", arrayOf(task.id.toString()))
        }
        db.close()
    }

    override fun taskById(id: Long, callback: (Task?) -> Unit) {
        val db = helper.writableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLOUMN_ID = ?"
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val note = if(cursor.moveToNext()) taskFromCursor(cursor) else null
        callback(note)
    }

    override fun search(term: String, callback: (List<Task>?) -> Unit) {
        var sql = "SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        if(term.isNotEmpty()){
            sql += "WHERE $COLOUMN_NAME LIKE ?"
            args = arrayOf("%$term%")
        }

        sql += "ORDER BY $COLOUMN_NAME"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val tasks = ArrayList<Task>()
        while (cursor.moveToNext()){
            val task = taskFromCursor(cursor)
            tasks.add(task)
        }
        cursor.close()
        db.close()
        callback(tasks)
    }

    fun selectAll(): ArrayList<Task>{

        val db = helper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val tasks = ArrayList<Task>()
        while (cursor.moveToNext()){
            val task = taskFromCursor(cursor)
            tasks.add(task)
        }
        cursor.close()
        db.close()
        return tasks
    }
    private fun insert(task: Task): Long
    {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLOUMN_NAME, task.name)
            put(COLOUMN_DESCRIPTION, task.description)
            put(COLOUMN_CHECKED, task.checked)
        }
        val id = db.insert(TABLE_NAME, null, cv)
        if(id != 1L){
            task.id = id;
        }
        db.close()
        return id;
    }

    private fun update(task: Task)
    {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLOUMN_NAME, task.name)
            put(COLOUMN_DESCRIPTION, task.description)
            put(COLOUMN_CHECKED, task.checked)
        }
        db.update(TABLE_NAME, cv, "$COLOUMN_ID = ?", arrayOf(task.id.toString()))
        db.close()
//        return note.id;
    }
    private  fun taskFromCursor(cursor: Cursor): Task{
        val id = cursor.getLong(cursor.getColumnIndex(COLOUMN_ID))
        val name = cursor.getString(cursor.getColumnIndex(COLOUMN_NAME))
        val description = cursor.getString(cursor.getColumnIndex(COLOUMN_DESCRIPTION))
        val checked = cursor.getInt(cursor.getColumnIndex(COLOUMN_CHECKED))
        return Task(name, description, checked, id)
    }
}