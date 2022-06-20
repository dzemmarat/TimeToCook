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
import ru.meowtee.timetocook.data.model.Difficult
import ru.meowtee.timetocook.data.model.Receipt
import kotlin.properties.Delegates

class SearchByTagViewModel: ViewModel() {
    private val _receipts = MutableStateFlow<List<Receipt>>(emptyList())
    val receipts: StateFlow<List<Receipt>> = _receipts

    private val db by lazy { RecipesDb.getInstance(context) }
    private var context: Context by Delegates.notNull()

    fun findReceipts(q: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _receipts.value = db.recipesDao().getRecipesByDifficult(q)
            Log.e("AAAAAAAAAAAAA", _receipts.value.toString())
        }
    }

    fun startDatabase(context: Context) {
        this.context = context
    }
}