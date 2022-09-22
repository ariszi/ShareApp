package zi.aris.data_provider.domain

/**
 * User
 * @property pinConfirmed This value doubles as a finished onboarding confirmation
 * @constructor Create empty User
 */
data class User(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val lastName: String = "",
    val telephone: String = "",
    val pin: String = "",
    val pinConfirmed: Boolean = false
)
