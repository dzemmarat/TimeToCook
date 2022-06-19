package ru.meowtee.timetocook.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.meowtee.timetocook.core.extensions.ioThread
import ru.meowtee.timetocook.data.db.RecipesDb
import ru.meowtee.timetocook.data.model.Receipt
import kotlin.properties.Delegates

class ReceiptInfoViewModel: ViewModel() {
    private val _receipts = MutableStateFlow<List<Receipt>>(emptyList())
    val receipts: StateFlow<List<Receipt>> = _receipts

    private val db by lazy { RecipesDb.getInstance(context) }
    private var context: Context by Delegates.notNull()

    fun changeReceipt(receipt: Receipt) {
        viewModelScope.launch(Dispatchers.IO) {
            db.recipesDao().updateReceipt(receipt.copy(isFavourite = !receipt.isFavourite))
        }
    }

    fun startDatabase(context: Context) {
        this.context = context
    }
}