package com.krause.domain.usecase

//class CreateTaskUseCase(private val repository: MemeRepository) :
//    UseCase<CreateTaskUseCase.Param, Task> {
//
//    data class Param(val nameTask: String, val isDone: Boolean = false)
//
//    override fun execute(param: Param): Task {
//        if (param.nameTask.isEmpty()) throw InvalidTaskError("Name Task was empty")
//        return repository.createTask(param.nameTask)
//    }
//}
//
//class InvalidTaskError(msg: String) : Error(msg)
