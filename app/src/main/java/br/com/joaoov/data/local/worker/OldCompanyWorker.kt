package br.com.joaoov.data.local.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.joaoov.Session
import br.com.joaoov.repository.CompanyRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

//TODO REMOVE
class OldCompanyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams), KoinComponent {

    private val companyRepository: CompanyRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            companyRepository.getOldCompanies().forEach {
                val newCompany = it.copy(userId = Session.user.id.orEmpty())
                companyRepository.update(newCompany)
            }
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}