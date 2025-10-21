package com.example.appbikeconsert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Importante para coletar o Flow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf // Para guardar o estado dos TextFields
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appbikeconsert.ui.theme.AppBikeConsertTheme

class MainActivity : ComponentActivity() {

    // 1. Instanciar o ViewModel usando a Factory que criamos
    private val viewModel: BikeConsertViewModel by viewModels {
        // Acessa o 'repository' que criamos na classe Application
        BikeConsertViewModelFactory((application as BikeConsertApplication).repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBikeConsertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. Chama a nossa tela principal
                    TelaPrincipal(viewModel = viewModel)
                }
            }
        }
    }
}

// ---- Esta é a nossa tela (UI) ----

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(viewModel: BikeConsertViewModel) {

    // 3. Coletar a lista de clientes do ViewModel
    // O 'collectAsState' transforma o Flow<List<Cliente>> em algo
    // que o Compose entende e reage (o 'State').
    val listaClientes by viewModel.todosClientes.collectAsState(initial = emptyList())

    // Variáveis para guardar o que o usuário digita nos campos
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bike Consert - Clientes") })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp) // Adiciona um padding geral
                .fillMaxSize()
        ) {

            // --- Seção para Adicionar Novo Cliente ---
            Text("Novo Cliente", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Cliente") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            Button(
                onClick = {
                    // 4. Chamar a função do ViewModel para salvar
                    if (nome.isNotBlank() && telefone.isNotBlank()) {
                        viewModel.insertCliente(nome, telefone)
                        // Limpar os campos após salvar
                        nome = ""
                        telefone = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Salvar Cliente")
            }

            // --- Seção da Lista de Clientes ---
            Text(
                "Clientes Cadastrados",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 24.dp)
            )

            // 5. Lista que mostra os clientes do banco
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(listaClientes) { cliente ->
                    ItemCliente(cliente = cliente)
                }
            }
        }
    }
}

// Composable para mostrar um item da lista
@Composable
fun ItemCliente(cliente: Cliente) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = cliente.nome, style = MaterialTheme.typography.titleMedium)
            Text(text = "Telefone: ${cliente.telefone}", style = MaterialTheme.typography.bodySmall)
        }
    }
}