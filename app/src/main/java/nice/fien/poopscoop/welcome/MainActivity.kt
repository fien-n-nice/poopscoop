package nice.fien.poopscoop.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nice.fien.poopscoop.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
