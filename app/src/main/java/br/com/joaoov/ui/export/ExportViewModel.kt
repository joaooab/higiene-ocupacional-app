package br.com.joaoov.ui.export

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.State
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.report.Report
import br.com.joaoov.repository.CompanyRepository
import br.com.joaoov.repository.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExportViewModel(
    private val companyRepository: CompanyRepository,
    private val reportRepository: ReportRepository
) : ViewModel() {

    private val _sendState = MutableLiveData<State<Unit>>()
    val sendState: LiveData<State<Unit>> = _sendState

    fun getCompanies() = companyRepository.getAll()

    fun getAllOfCompany(company: Company) = reportRepository.getAllOfCompany(company)

    fun send(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            _sendState.postValue(State.Loading())
            runCatching {
                reportRepository.send(report)
            }.onSuccess {
                _sendState.postValue(State.Success(Unit))
            }.onFailure {
                _sendState.postValue(State.Error(it))
            }
        }
    }

}