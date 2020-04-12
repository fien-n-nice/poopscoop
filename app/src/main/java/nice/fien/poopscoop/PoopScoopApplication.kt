package nice.fien.poopscoop

import android.app.Application
import timber.log.Timber

/*
 * Custom Application class in order to use logging library Timber.
 * This class must be reference from AndroidManifest.xml so that it is used.
 */
class PoopScoopApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Instantiate Timber logging library for our app, so that it can be used in our activities.
        Timber.plant(Timber.DebugTree())
    }
}