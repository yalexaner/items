package yalexaner.items.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import yalexaner.items.db.Order
import yalexaner.items.db.OrderRepository

class NewOrderViewModel(private val repository: OrderRepository) : ViewModel() {
    private val _itemsCollected = MutableLiveData<String>()
    val itemsCollected: LiveData<String> get() = _itemsCollected

    private val _itemsOrdered = MutableLiveData<String>()
    val itemsOrdered: LiveData<String> get() = _itemsOrdered

    fun onItemsCollectedChange(value: String) {
        _itemsCollected.value = value
    }

    fun onItemsOrderedChange(value: String) {
        _itemsOrdered.value = value
    }

    fun addOrder(order: Order) {
        viewModelScope.launch {
            repository.insert(order)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NewOrderViewModelFactory(private val repository: OrderRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewOrderViewModel::class.java)) {
            return NewOrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}