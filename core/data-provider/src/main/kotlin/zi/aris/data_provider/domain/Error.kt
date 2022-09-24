package zi.aris.data_provider.domain


object GenericError : Result.Error()

data class ErrorWithMessage(val message: String) : Result.Error()


