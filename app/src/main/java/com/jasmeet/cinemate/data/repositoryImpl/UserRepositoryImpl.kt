package com.jasmeet.cinemate.data.repositoryImpl

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.cinemate.data.data.UserInfo
import com.jasmeet.cinemate.data.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth :FirebaseAuth,
    private val db : FirebaseFirestore
) : UserRepository {


    override suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email,password).await()
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email,password).await()
    }

    override suspend fun saveUserDataToFirestore(authResult: AuthResult) {
        val user = authResult.user ?: return

        db.collection("users").document(user.uid).set(
            UserInfo(
                name = user.displayName ?:user.email.toString().substringBefore("@"),
                email = user.email,
                uid = user.uid,
                imgUrl = if (user.photoUrl != null) user.photoUrl.toString() else "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
            )
        ).await()
    }
}

