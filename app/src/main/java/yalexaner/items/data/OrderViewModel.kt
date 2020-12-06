package yalexaner.items.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import yalexaner.items.db.Order
import yalexaner.items.db.OrderRepository

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    val items: LiveData<Int> = repository.itemsCount.asLiveData()
    val itemsForToday: LiveData<Int> = repository.itemsCountForToday.asLiveData()
    val orders: LiveData<List<Order>> = repository.allOrders.asLiveData()

    fun addItems(order: Order) = viewModelScope.launch {
        repository.insert(order)
    }
}

class OrderViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}