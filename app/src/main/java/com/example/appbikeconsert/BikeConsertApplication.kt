package com.example.appbikeconsert

import android.app.Application

class BikeConsertApplication : Application() {

    // "lazy" significa que o banco de dados e o repositório
    // só serão criados na primeira vez que forem usados.

    // Cria a instância do banco de dados
    val database by lazy { AppDatabase.getDatabase(this) }

    // Cria a instância do repositório, passando o DAO do banco
    val repository by lazy { BikeConsertRepository(database.bikeConsertDao()) }
}