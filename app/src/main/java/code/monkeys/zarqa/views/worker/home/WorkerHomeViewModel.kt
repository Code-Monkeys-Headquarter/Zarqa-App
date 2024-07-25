package code.monkeys.zarqa.views.worker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import code.monkeys.zarqa.repository.ProductRepository

class WorkerHomeViewModel(private val productRepository: ProductRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun getTotalItems(): LiveData<Int> = productRepository.getTotalProducts()

    fun getTotalStock(): LiveData<Int> = productRepository.getTotalStocks()

    fun getTotalValue(): LiveData<Int> = productRepository.getTotalValue()

    fun getItemsAddedToday(currentDate: String): LiveData<Int> = productRepository.getItemsAddedToday(currentDate)

    fun getItemsOutToday(currentDate: String): LiveData<Int> = productRepository.getItemsOutToday(currentDate)

    fun getStockInToday(currentDate: String): LiveData<Int> = productRepository.getStockInToday(currentDate)

    fun getStockOutToday(currentDate: String): LiveData<Int> = productRepository.getStockOutToday(currentDate)
}