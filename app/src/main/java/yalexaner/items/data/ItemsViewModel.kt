package yalexaner.items.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsViewModel : ViewModel() {
    private var _items = MutableLiveData(0)

    val items: LiveData<Int> = _items

    fun addItems(items: Int) {
        _items.value = (_items.value ?: 0) + items
    }
}