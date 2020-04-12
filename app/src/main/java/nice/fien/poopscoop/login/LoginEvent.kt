package nice.fien.poopscoop.login

sealed class LoginEvent<out T> {
    object OnEmailLoginButtonClick : LoginEvent<Nothing>()
    object OnGoogleLoginButtonClick : LoginEvent<Nothing>()
    object OnStart : LoginEvent<Nothing>()
    data class OnEmailSignInResult<out LoginResult>(val result: LoginResult) : LoginEvent<LoginResult>()
    data class OnGoogleSignInResult<out LoginResult>(val result: LoginResult) : LoginEvent<LoginResult>()
}