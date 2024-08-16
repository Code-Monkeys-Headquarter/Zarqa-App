package code.monkeys.zarqa.views.worker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.response.DataDashboardSummary
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class WorkerHomeViewModel(private var repository: Repository) : ViewModel() {

    private val _dashboardSummary = MutableLiveData<DataDashboardSummary?>()
    val dashboardSummary: MutableLiveData<DataDashboardSummary?> get() = _dashboardSummary

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _code = MutableLiveData<Int>()
    val code: LiveData<Int> get() = _code

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchDashboardSummary(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDashboardSummary(token)
                if (response.isSuccessful) {
                    response.body()?.let { dashboardResponse ->
                        _dashboardSummary.postValue(dashboardResponse.data)
                        _status.postValue(if (dashboardResponse.success == true) "Success" else "Failed")
                        _code.postValue(dashboardResponse.code ?: 0)
                    }
                } else {
                    _status.postValue("Failed")
                    _code.postValue(response.code())
                    _errorMessage.postValue(
                        "Error: ${response.message()} - ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: HttpException) {
                _status.postValue("Error: ${e.message}")
                _code.postValue(e.code())
                _errorMessage.postValue("HTTP Error: ${e.message}")
            } catch (e: Exception) {
                _status.postValue("Error: ${e.message}")
                _code.postValue(-1)
                _errorMessage.postValue("Unknown Error: ${e.message}")
            }
        }
    }

}