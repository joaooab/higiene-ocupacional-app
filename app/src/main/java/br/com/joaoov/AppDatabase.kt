package br.com.joaoov

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.AmbientDAO
import br.com.joaoov.data.Company
import br.com.joaoov.data.CompanyDAO

@Database(
    version = 4,
    entities = [Company::class, Ambient::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ambientDAO(): AmbientDAO
    abstract fun companyDAO(): CompanyDAO

    companion object {
        private const val NOME_BANCO_DE_DADOS = "higieneocupacional.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, NOME_BANCO_DE_DADOS)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
