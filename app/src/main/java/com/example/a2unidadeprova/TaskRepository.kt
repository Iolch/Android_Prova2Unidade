package com.example.a2unidadeprova

interface TaskRepository {
    fun save(task: Task);
    fun remove(vararg tasks: Task);
    fun taskById(id: Long, callback: (Task?) -> Unit);
    fun search(term: String, callback: (List<Task>?) -> Unit);

}