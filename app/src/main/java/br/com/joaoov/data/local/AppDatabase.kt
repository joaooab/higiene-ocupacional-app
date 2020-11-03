package br.com.joaoov.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.joaoov.data.local.ambient.AmbientDAO
import br.com.joaoov.data.local.ambient.AmbientLocal
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.company.CompanyDAO
import br.com.joaoov.data.local.departament.DepartamentDAO
import br.com.joaoov.data.local.departament.DepartamentLocal
import br.com.joaoov.data.local.function.FunctionDAO
import br.com.joaoov.data.local.function.FunctionLocal

@Database(
    version = 9,
    entities = [Company::class, DepartamentLocal::class, AmbientLocal::class, FunctionLocal::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDAO(): CompanyDAO
    abstract fun departamentDAO(): DepartamentDAO
    abstract fun ambientDAO(): AmbientDAO
    abstract fun functionDAO(): FunctionDAO

    companion object {
        private const val DATABASE_NAME = "higieneocupacional.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}