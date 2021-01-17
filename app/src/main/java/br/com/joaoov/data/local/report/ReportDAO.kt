package br.com.joaoov.data.local.report

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ReportDAO {

    @Query("SELECT * FROM company WHERE id =:companyId")
    fun getByCompany(companyId: Long): LiveData<CompanyWithDepartamentsLocal>

}
