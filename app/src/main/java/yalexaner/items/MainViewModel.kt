package yalexaner.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _items = MutableLiveData(0)

    val items: LiveData<Int> = _items

    fun addItems(items: Int) {
        _items.value = (_items.value ?: 0) + items
    }
}