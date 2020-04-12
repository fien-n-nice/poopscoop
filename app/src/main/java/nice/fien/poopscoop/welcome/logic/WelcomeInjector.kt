package nice.fien.poopscoop.welcome.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WelcomeInjector(application: Application): AndroidViewModel(application) {

    fun provideMainViewModelFactory(): MainViewModelFactory = MainViewModelFactory()
}