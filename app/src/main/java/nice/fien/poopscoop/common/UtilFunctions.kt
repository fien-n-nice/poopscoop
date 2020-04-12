package nice.fien.poopscoop.common

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*
 * Await for task to finish, but be indifferent as to result, using suspendCoroutine builder.
 * Wrapper function for callbacks, ie. Firebase/GMS calls.
 */
internal suspend fun <T> awaitTaskCompletable(task: Task<T>): Unit = suspendCoroutine { continuation ->
    task.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception!!)
        }
    }
}