package com.example.appbikeconsert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// O ViewModel precisa do Repository para buscar/salvar dados.
class BikeConsertViewModel(private val repository: BikeConsertRepository) : ViewModel() {

    // Expõe a lista de clientes para a UI (Interface do Usuário)
    val todosClientes: Flow<List<Cliente>> = repository.todosClientes

    // Função para inserir um novo cliente
    // viewModelScope.launch inicia uma Coroutine segura.
    fun insertCliente(nome: String, telefone: String) = viewModelScope.launch {
        val novoCliente = Cliente(nome = nome, telefone = telefone)
        repository.insertCliente(novoCliente)
    }

    // Função para inserir uma nova Ordem de Serviço
    fun insertOrdemServico(
        clienteId: Int,
        descricao: String,
        status: String
    ) = viewModelScope.launch {
        val novaOS = OrdemServico(
            clienteId = clienteId,
            descricaoServico = descricao,
            status = status
            // A data de entrada é gerada automaticamente (veja a Entity OrdemServico.kt)
        )
        repository.insertOrdemServico(novaOS)
    }

    // Função para buscar as ordens de um cliente
    fun getOrdensDoCliente(clienteId: Int): Flow<List<OrdemServico>> {
        return repository.getOrdensDoCliente(clienteId)
    }
}

// ---- ESTA É A "FÁBRICA" ----
// Esta classe ensina o Android como CRIAR o seu BikeConsertViewModel,
// já que ele agora precisa de um "repository" para ser construído.
class BikeConsertViewModelFactory(
    private val repository: BikeConsertRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BikeConsertViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BikeConsertViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}