package com.example.theloffle.features.recetas.domain.model

enum class Etiqueta(val categoria: Categoria) {
    // DIETA
    VEGETARIANO(Categoria.DIETA),
    VEGANO(Categoria.DIETA),
    CELIACO(Categoria.DIETA),

    // PAIS
    ESPANIOLA(Categoria.PAIS),
    ITALIANA(Categoria.PAIS),
    CHINA(Categoria.PAIS),
    INDIA(Categoria.PAIS),
    JAPONESA(Categoria.PAIS),
    NEPALI(Categoria.PAIS),
    COREANA(Categoria.PAIS),
    PERUANA(Categoria.PAIS)
}