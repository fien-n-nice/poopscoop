package nice.fien.poopscoop.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import nice.fien.poopscoop.R
import kotlinx.android.synthetic.main.login_fragment.*
import nice.fien.poopscoop.common.RC_SIGN_IN
import nice.fien.poopscoop.login.LoginEvent
import nice.fien.poopscoop.login.LoginResult
import nice.fien.poopscoop.login.logic.LoginInjector
import nice.fien.poopscoop.welcome.MainActivity
import timber.log.Timber

class LoginView : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(
            this,
            LoginInjector(requireActivity().application).provideUserViewModelFactory()
        ).get(UserViewModel::class.java)

        setUpClickListeners()
        observeViewModel()
    }

    private fun setUpClickListeners() {
        btnLogin.setOnClickListener { viewModel.handleEvent(LoginEvent.OnEmailLoginButtonClick) }
        btnGoogleLogin.setOnClickListener { viewModel.handleEvent(LoginEvent.OnGoogleLoginButtonClick) }

//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            startWelcomeActivity()
//        }
    }

    // Navigation doesn't play nicely with onActivityResult, so we are not using it here.
    private fun startWelcomeActivity() = requireActivity().startActivity(
        Intent(
            activity,
            MainActivity::class.java
        )
    )

    private fun observeViewModel() {
        viewModel.loginStatus.observe(
            viewLifecycleOwner,
            Observer { txtLoggedInStatus.text = it }
        )

        viewModel.loginButtonText.observe(
            viewLifecycleOwner,
            Observer { btnLogin.text = it }
        )

        viewModel.authAttempt.observe(
            viewLifecycleOwner,
            Observer { startSignInFlow() }
        )
    }

    private fun startSignInFlow() {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // this is generated dynamically from google-services.json file
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        var userToken: String? = null

        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

            if (account != null) userToken = account.idToken
        } catch (e: Exception) {
            Timber.e("Google authentication failed, ${e.message}")
        }

        viewModel.handleEvent(
            LoginEvent.OnGoogleSignInResult(
                LoginResult(requestCode, userToken)
            )
        )
    }
}