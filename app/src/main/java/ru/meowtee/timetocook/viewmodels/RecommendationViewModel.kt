package ru.meowtee.timetocook.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.meowtee.timetocook.core.extensions.ioThread
import ru.meowtee.timetocook.data.db.RecipesDb
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.data.model.Recommendation
import kotlin.properties.Delegates

class RecommendationViewModel : ViewModel() {
    private val _recommendations = MutableStateFlow<List<Recommendation>>(emptyList())
    val recommendations = _recommendations.asStateFlow()

    private val db by lazy { RecipesDb.getInstance(context) }
    private var context: Context by Delegates.notNull()

    fun findRecommendations() {
        viewModelScope.launch(Dispatchers.IO) {
            _recommendations.value = db.recommendationsDao().getAllRecommendations()
        }
    }

    fun startDatabase(context: Context) {
        this.context = context

        viewModelScope.launch(Dispatchers.IO) {
            db.recipesDao().getAllRecipes()
        }
    }
}