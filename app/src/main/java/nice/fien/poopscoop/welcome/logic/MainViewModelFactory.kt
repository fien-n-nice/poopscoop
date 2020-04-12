package nice.fien.poopscoop.welcome.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nice.fien.poopscoop.welcome.MainViewModel

class MainViewModelFactory: ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}