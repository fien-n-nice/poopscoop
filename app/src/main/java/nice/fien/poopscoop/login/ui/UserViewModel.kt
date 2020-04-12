package nice.fien.poopscoop.login.ui

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import nice.fien.poopscoop.common.*
import nice.fien.poopscoop.common.LOGGED_OUT
import nice.fien.poopscoop.common.LOGIN
import nice.fien.poopscoop.common.LOGIN_ERROR
import nice.fien.poopscoop.common.LOGIN_LOADING
import nice.fien.poopscoop.login.LoginEvent
import nice.fien.poopscoop.login.LoginResult
import nice.fien.poopscoop.login.model.repository.IUserRepository
import nice.fien.poopscoop.user.model.User
import kotlin.coroutines.CoroutineContext
import nice.fien.poopscoop.common.Result;

/*
 * Todo: eventually, we want some kind of more elegant way to show the user that they are signed in/out.
 */
class UserViewModel(val repo: IUserRepository, uiContext: CoroutineContext) : BaseViewModel<LoginEvent<LoginResult>>(uiContext) {

    // Data model
    private val userState = MutableLiveData<User>()

    internal val authAttempt = MutableLiveData<Unit>()

    // Ui binding
    internal val loginButtonText = MutableLiveData<String>()
    internal val loginStatus = MutableLiveData<String>()

    // The various states of login view

    private fun showErrorState() {
        loginButtonText.value = LOGIN
        loginStatus.value = LOGIN_ERROR
    }

    private fun showLoadingState() {
        loginStatus.value = LOGIN_LOADING
    }

    private fun showLoggedOutState() {
        loginStatus.value = LOGGED_OUT
        loginButtonText.value = LOGIN
    }

    private fun showLoggedInState() {
        loginStatus.value = LOGGED_IN
        loginButtonText.value = LOGOUT
    }

    override fun handleEvent(event: LoginEvent<LoginResult>) {
        //Trigger loading screen first
        showLoadingState()
        when (event) {
            is LoginEvent.OnStart -> getUser()
            is LoginEvent.OnEmailLoginButtonClick -> onAuthButtonClick()
            is LoginEvent.OnGoogleLoginButtonClick-> onAuthButtonClick()
            is LoginEvent.OnGoogleSignInResult -> onLoginResult(event.result)
            is LoginEvent.OnEmailSignInResult -> onLoginResult(event.result)
        }
    }

    private fun getUser() = launch {
        val result = repo.getCurrentUser()
        when (result) {
            is Result.Value -> {
                userState.value = result.value
                if (result.value == null) showLoggedOutState()
                else showLoggedInState()
            }
            is Result.Error -> showErrorState()
        }
    }

    /*
     * Upon authenticating via either method, check if user is null before beginning authAttempt.
     */
    private fun onAuthButtonClick() {
        if (userState.value == null) authAttempt.value = Unit
        else logooutUser()
    }

    private fun onLoginResult(result: LoginResult) = launch {
        if (result.requestCode == RC_SIGN_IN && result.userToken != null) {
            val createGoogleUserResult = repo.loginGoogleUser(result.userToken)

            // ie. if was successful
            if (createGoogleUserResult is Result.Value) getUser()
            else showErrorState()
        }
    }

    private fun logooutUser() = launch {
        val result = repo.logoutCurrentUser()

        // Just Kotlin's switch-statement, in case you were wondering.
        when (result) {
            is Result.Value -> {
                userState.value = null
                showLoggedOutState()
            }
            is Result.Error -> showErrorState()
        }
    }
}