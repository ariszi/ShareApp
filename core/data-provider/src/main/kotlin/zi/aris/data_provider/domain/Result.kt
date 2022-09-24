package zi.aris.data_provider.domain

sealed class Result<out T> {

    abstract class Error : Result<Nothing>()

    data class Success<T>(val data: T) : Result<T>()
}
