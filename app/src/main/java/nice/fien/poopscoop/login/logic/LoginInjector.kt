package nice.fien.poopscoop.login.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import nice.fien.poopscoop.login.model.implementations.FirebaseUserRepoImpl
import nice.fien.poopscoop.login.model.repository.IUserRepository

class LoginInjector(application: Application) : AndroidViewModel(application) {
    init {
        FirebaseApp.initializeApp(application)
    }

    private fun getUserRepository() : IUserRepository {
        return FirebaseUserRepoImpl()
    }

    fun provideUserViewModelFactory(): UserViewModelFactory =
        UserViewModelFactory(getUserRepository())
}