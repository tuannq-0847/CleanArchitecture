package com.krause.domain.usecase

import com.krause.domain.model.Task
import com.krause.domain.repository.MemeRepository

class GetUseCase constructor(
    private val repository: MemeRepository
) : WithoutParamUseCase<List<Task>> {


    override fun execute(): List<Task> {
        return repository.getTask().subList(0,5)
    }
}
