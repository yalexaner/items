package yalexaner.items.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import yalexaner.items.db.Order
import yalexaner.items.db.OrderRepository

class MainViewModel(private val repository: OrderRepository) : ViewModel() {

    val itemsForToday: LiveData<Int> = repository.itemsCountForToday.asLiveData()
    val orders: LiveData<List<Order>> = repository.allOrders.asLiveData()

    fun addOrder(order: Order) {
        viewModelScope.launch {
            repository.insert(order)
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}