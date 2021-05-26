package com.krause.domain.repository

import com.krause.domain.model.Task

interface TaskRepository {

    fun getTask(): List<Task>

    fun createTask(nameTask: String, isDone: Boolean = false): Task
}
