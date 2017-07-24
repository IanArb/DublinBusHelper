package com.ianarbuckle.dublinbushelper.firebase.authentication

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.ianarbuckle.dublinbushelper.firebase.RequestListener

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

interface FirebaseAuthHelper {
    fun googleLogin(account: GoogleSignInAccount, listener: RequestListener)
    fun registerUser(email: String, password: String, listener: RequestListener)
    fun signInUser(email: String, password: String, listener: RequestListener)
    fun logOutUser()
    val isUserSignedIn: Boolean
    val userDisplayName: String?
    val userPhoto: String?
    val userEmail: String?
}
