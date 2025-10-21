package com.example.appbikeconsert

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ordens_servico",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],  // Coluna na tabela Cliente (pai)
            childColumns = ["clienteId"], // Coluna nesta tabela (filha)
            onDelete = ForeignKey.CASCADE // Se apagar um cliente, apaga suas ordens
        )
    ]
)
data class OrdemServico(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val clienteId: Int, // Chave que liga ao Cliente

    val descricaoServico: String,
    val dataEntrada: Long = System.currentTimeMillis(), // Salva a data como um número Long
    val status: String // Ex: "Aguardando", "Em Andamento", "Concluído"
)