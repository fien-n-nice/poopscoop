package nice.fien.poopscoop.welcome

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nice.fien.poopscoop.login.LoginActivity
import nice.fien.poopscoop.user.model.User

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val loggedInState = MutableLiveData<User>();

    fun handleEvent() {

    }

}
