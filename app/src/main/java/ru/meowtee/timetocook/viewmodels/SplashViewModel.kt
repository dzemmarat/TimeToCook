package ru.meowtee.timetocook.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.meowtee.timetocook.core.extensions.ioThread
import ru.meowtee.timetocook.data.db.RecipesDb
import ru.meowtee.timetocook.data.model.Receipt
import kotlin.properties.Delegates

class SplashViewModel : ViewModel() {
    private val db by lazy { RecipesDb.getInstance(context) }
    private var context: Context by Delegates.notNull()

    fun startDatabase(context: Context) {
        this.context = context
        ioThread {
            db.recipesDao().getAllRecipes()
        }
    }
}