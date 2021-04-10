package br.com.joaoov.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.joaoov.data.local.ambient.AmbientDAO
import br.com.joaoov.data.local.ambient.AmbientLocal
import br.com.joaoov.data.local.company.CompanyDAO
import br.com.joaoov.data.local.company.CompanyLocal
import br.com.joaoov.data.local.departament.DepartamentDAO
import br.com.joaoov.data.local.departament.DepartamentLocal
import br.com.joaoov.data.local.function.FunctionDAO
import br.com.joaoov.data.local.function.FunctionLocal
import br.com.joaoov.data.local.report.ReportDAO
import br.com.joaoov.data.local.resource.*
import br.com.joaoov.data.local.risk.RiskDAO
import br.com.joaoov.data.local.risk.RiskLocal
import br.com.joaoov.data.local.syncronize.SyncronizeDAO
import br.com.joaoov.data.local.syncronize.SyncronizeLocal

@Database(
    version = 25,
    entities = [
        CompanyLocal::class,
        DepartamentLocal::class,
        AmbientLocal::class,
        FunctionLocal::class,
        SyncronizeLocal::class,
        ResourceRiskLocal::class,
        ResourceAmbientLocal::class,
        ResourceAgentLocal::class,
        RiskLocal::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDAO(): CompanyDAO
    abstract fun departamentDAO(): DepartamentDAO
    abstract fun ambientDAO(): AmbientDAO
    abstract fun functionDAO(): FunctionDAO
    abstract fun syncronizeDAO(): SyncronizeDAO
    abstract fun resourceRiskDAO(): ResourceRiskDAO
    abstract fun resourceAmbientDAO(): ResourceAmbientDAO
    abstract fun resourceAgentDAO(): ResourceAgentDAO
    abstract fun riskDAO(): RiskDAO
    abstract fun reportDAO(): ReportDAO

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
                .addMigrations(*DatabaseMigrations.getMigrations())
                .allowMainThreadQueries()
                .build()
        }
    }
}

object DatabaseMigrations {

    fun getMigrations(): Array<Migration> {
        return arrayOf(
            MIGRATION_24_25,
        )
    }

    private val MIGRATION_24_25: Migration = object : Migration(24, 25) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE function ADD COLUMN amount INTEGER")
        }
    }

}