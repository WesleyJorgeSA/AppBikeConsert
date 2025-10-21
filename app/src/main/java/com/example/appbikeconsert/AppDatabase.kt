package com.example.appbikeconsert
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cliente::class, OrdemServico::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // O Room vai implementar esta função para você
    abstract fun bikeConsertDao(): BikeConsertDao

    companion object {
        // @Volatile garante que o valor de INSTANCE esteja sempre atualizado
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna a instância se ela já existir
            // Se não, cria o banco de dados
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bike_consert_db" // Nome do arquivo do banco
                ).build()
                INSTANCE = instance
                // retorna a instância
                instance
            }
        }
    }
}