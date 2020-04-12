package nice.fien.poopscoop.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import nice.fien.poopscoop.R
import nice.fien.poopscoop.login.ui.LoginView

private const val LOGIN_VIEW = "LOGIN_VIEW"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        FirebaseApp.initializeApp(applicationContext)

        val view = supportFragmentManager.findFragmentByTag(LOGIN_VIEW) as LoginView?
            ?: LoginView()

        supportFragmentManager.beginTransaction()
            .replace(R.id.loginActivity, view, LOGIN_VIEW)
            .commitNowAllowingStateLoss()
    }
}