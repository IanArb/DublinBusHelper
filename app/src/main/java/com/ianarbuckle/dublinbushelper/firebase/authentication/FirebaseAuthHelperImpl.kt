package com.ianarbuckle.dublinbushelper.firebase.authentication

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ianarbuckle.dublinbushelper.firebase.RequestListener

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

class FirebaseAuthHelperImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthHelper {

    override fun googleLogin(account: GoogleSignInAccount, listener: RequestListener) {
        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener { task ->
                    listener.onSuccess()
                    if (!task.isSuccessful) {
                        listener.onFailure()
                    } else {
                        listener.onSuccess()
                    }
                }
    }

    override fun registerUser(email: String, password: String, listener: RequestListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onSuccess()
                    } else {
                        listener.onFailure()
                    }
                }
    }

    override fun signInUser(email: String, password: String, listener: RequestListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onSuccess()
                    } else {
                        listener.onFailure()
                    }
                }
    }

    override fun logOutUser() {
        if (isUserSignedIn) {
            firebaseAuth.signOut()
        }
    }

    override val userDisplayName: String?
        get() {
            val firebaseUser = firebaseAuth.currentUser
            return firebaseUser?.displayName
        }

    override val isUserSignedIn: Boolean
        get() = !userDisplayName.isNullOrEmpty()

    override val userPhoto: String?
        get() {
            val firebaseUser = firebaseAuth.currentUser
            return firebaseUser?.photoUrl.toString()
        }

    override val userEmail: String?
        get() {
            val firebaseUser = firebaseAuth.currentUser
            return firebaseUser?.email
        }
}
