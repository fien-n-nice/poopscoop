package nice.fien.poopscoop.login.model.implementations

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nice.fien.poopscoop.common.Result
import nice.fien.poopscoop.common.awaitTaskCompletable
import nice.fien.poopscoop.login.model.repository.IUserRepository
import nice.fien.poopscoop.user.model.User
import java.lang.Exception

class FirebaseUserRepoImpl(val auth: FirebaseAuth = FirebaseAuth.getInstance()) : IUserRepository {

    override suspend fun getCurrentUser(): Result<Exception, User?> {
        // Firebase has already been initialized in constructor, with the getInstance() call.
        // It is generally a good practice to initialize ahead of time.
        val firebaseUser = auth.currentUser

        return if (firebaseUser == null) {
            Result.build { null }
        } else {
            Result.build { User(firebaseUser.uid) } // Our domain model for User.
        }
    }

    override suspend fun logoutCurrentUser(): Result<Exception, Unit> {
        return Result.build {
            auth.signOut() // Sign out is nice and simple with Firebase.
        }
    }

    override suspend fun loginEmailUser(idToken: String): Result<Exception, Unit> {
        TODO("Not yet implemented")
    }

    /*
     * Used to create a new user if user does not exist and login pre-existing user.
     * We wrap the function body using withContext(Dispatchers.IO) coroutine builder
     * to move operation safely into a brackground thread.
     */
    override suspend fun loginGoogleUser(idToken: String): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null) // Request credential from Google Auth Provider
            awaitTaskCompletable(auth.signInWithCredential(credential)) // Give credential to Firebase auth
            Result.build { Unit }
        } catch (e: Exception) {
            Result.build { throw e }
        }
    }

}