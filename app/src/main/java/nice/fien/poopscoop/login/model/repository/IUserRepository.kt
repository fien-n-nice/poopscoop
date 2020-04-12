package nice.fien.poopscoop.login.model.repository

import nice.fien.poopscoop.common.Result
import nice.fien.poopscoop.user.model.User
import java.lang.Exception

interface IUserRepository {

    suspend fun getCurrentUser(): Result<Exception, User?>

    suspend fun logoutCurrentUser(): Result<Exception, Unit>

    suspend fun loginEmailUser(idToken: String): Result<Exception, Unit>

    suspend fun loginGoogleUser(idToken: String): Result<Exception, Unit>

}