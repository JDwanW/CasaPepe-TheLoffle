package com.example.theloffle

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform