package com.example.appbikeconsert
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeConsertDao {



    // --- Funções do Cliente ---



    @Insert

    suspend fun insertCliente(cliente: Cliente) // 'suspend' para rodar fora da thread principal



    @Update

    suspend fun updateCliente(cliente: Cliente)



    @Query("SELECT * FROM clientes ORDER BY nome ASC")

    fun getAllClientes(): Flow<List<Cliente>> // 'Flow' faz a lista atualizar sozinha na tela



    @Query("SELECT * FROM clientes WHERE id = :clienteId")

    fun getClienteById(clienteId: Int): Flow<Cliente>





    // --- Funções da Ordem de Serviço ---



    @Insert

    suspend fun insertOrdemServico(os: OrdemServico)



    @Update

    suspend fun updateOrdemServico(os: OrdemServico)



    @Query("SELECT * FROM ordens_servico WHERE clienteId = :idDoCliente ORDER BY dataEntrada DESC")

    fun getOrdensDoCliente(idDoCliente: Int): Flow<List<OrdemServico>>

}