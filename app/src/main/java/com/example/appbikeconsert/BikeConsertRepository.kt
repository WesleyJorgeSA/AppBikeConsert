package com.example.appbikeconsert

import kotlinx.coroutines.flow.Flow

// O repositório precisa do DAO para acessar o banco de dados.
// Dizemos que o DAO é uma "dependência" do repositório.
class BikeConsertRepository(private val dao: BikeConsertDao) {

    // Esta variável vai "fluir" (Flow) a lista de clientes
    // e se atualizar automaticamente quando o banco mudar.
    val todosClientes: Flow<List<Cliente>> = dao.getAllClientes()

    // Função para buscar as ordens de um cliente específico
    fun getOrdensDoCliente(clienteId: Int): Flow<List<OrdemServico>> {
        return dao.getOrdensDoCliente(clienteId)
    }

    // --- Funções "suspend" para Inserir/Atualizar ---
    // Elas precisam ser chamadas de dentro de uma Coroutine (o ViewModel fará isso)

    suspend fun insertCliente(cliente: Cliente) {
        dao.insertCliente(cliente)
    }

    suspend fun insertOrdemServico(os: OrdemServico) {
        dao.insertOrdemServico(os)
    }

    // (Você pode adicionar as funções de 'update' aqui quando precisar)
}