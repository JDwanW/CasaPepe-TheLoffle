package com.example.theloffle.features.inicio.presentation.model

import androidx.lifecycle.ViewModel
import com.example.theloffle.features.inicio.domain.repository.AuthRepo

class LogInViewModell (
    private val repository: AuthRepo
): ViewModel(){

    fun login(email: String, password: String){
        
    }
}