package com.krause.domain.usecase

interface UseCase<in Param, out T> {
    fun execute(param: Param): T
}

interface WithoutParamUseCase<out T>{
    fun execute():T
}
