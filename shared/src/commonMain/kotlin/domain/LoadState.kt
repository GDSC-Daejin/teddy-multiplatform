package domain

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class LoadState<T> {

    fun getDataOrNull() = when (this) {
        is Success<T> -> data
        else -> null
    }

    class Loading<T> : LoadState<T>()
    class Success<T>(val data: T) : LoadState<T>()
    class Error<T>(val error: Throwable) : LoadState<T>() {
        init {
            error.printStackTrace()
        }
    }
}

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isLoading() : Boolean {
    contract {
        returns(true) implies (this@isLoading is LoadState.Loading<T>)
    }
    return this is LoadState.Loading
}

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isSuccess() : Boolean {
    contract {
        returns(true) implies (this@isSuccess is LoadState.Success<T>)
    }
    return this is LoadState.Success
}

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isError() : Boolean {
    contract {
        returns(true) implies (this@isError is LoadState.Error<T>)
    }
    return this is LoadState.Error
}