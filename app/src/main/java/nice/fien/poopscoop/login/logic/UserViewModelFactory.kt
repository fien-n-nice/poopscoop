package nice.fien.poopscoop.login.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import nice.fien.poopscoop.login.model.repository.IUserRepository
import nice.fien.poopscoop.login.ui.UserViewModel

class UserViewModelFactory(private val userRepo: IUserRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return UserViewModel(userRepo, Dispatchers.Main) as T
    }
}