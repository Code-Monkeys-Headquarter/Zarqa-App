package code.monkeys.zarqa.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.views.auth.login.LoginViewModel
import code.monkeys.zarqa.views.auth.register.RegisterViewModel
import code.monkeys.zarqa.views.worker.warehouse.product.add.AddProductViewModel
import code.monkeys.zarqa.views.worker.warehouse.product.detail.DetailProductViewModel
import code.monkeys.zarqa.views.worker.warehouse.product.restock.TakeProductViewModel
import code.monkeys.zarqa.views.worker.warehouse.tab.allproduct.AllProductViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TakeProductViewModel::class.java)) {
            return TakeProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            return AddProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AllProductViewModel::class.java)) {
            return AllProductViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}