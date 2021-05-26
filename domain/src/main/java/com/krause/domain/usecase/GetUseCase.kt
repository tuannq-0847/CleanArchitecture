package com.krause.domain.usecase

import com.krause.domain.model.Task
import com.krause.domain.repository.TaskRepository

class GetUseCase constructor(
    private val repository: TaskRepository
) : WithoutParamUseCase<List<Task>> {


    override fun execute(): List<Task> {
        return repository.getTask().subList(0,5)
    }
}
