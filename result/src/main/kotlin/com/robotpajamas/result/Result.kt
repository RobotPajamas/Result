package com.robotpajamas.result

sealed class Result<Value> {
    abstract val value: Value?
    abstract val error: Exception?

    class Success<Value : Any>(override val value: Value) : Result<Value>() {
        override val error: Exception? = null
    }

    class Failure<Nothing>(override val error: Exception) : Result<Nothing>() {
        override val value: Nothing? = null
    }

    val isSuccess: Boolean
        get() = when (this) {
            is Success -> true
            else -> false
        }

    val isFailure: Boolean
        get() = !isSuccess

    fun success(call: (Value) -> Unit) {
        if (this is Success) {
            call(value)
        }
    }

    fun failure(call: (Exception) -> Unit) {
        if (this is Failure) {
            call(error)
        }
    }

    @Throws(Exception::class)
    fun unwrap(): Value = when (this) {
        is Success -> value
        is Failure -> throw error
    }
}
