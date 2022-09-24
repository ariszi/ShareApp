package zi.aris.data_provider.data

data class UnauthorisedException(override val message: String = "Pin is not valid. Please try again") : Throwable()
