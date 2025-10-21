package com.example.appbikeconsert

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val telefone: String
    // Você pode adicionar outros campos aqui, ex: val email: String?
)