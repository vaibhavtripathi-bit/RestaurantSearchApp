package com.example.myrestaurants.domain

abstract class UseCase<T, Params> {

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    abstract suspend fun execute(params: Params): T
}