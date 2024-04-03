package com.jasmeet.cinemate.domain

import com.google.firebase.auth.AuthResult

interface UserRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun saveUserDataToFirestore(authResult: AuthResult)
}