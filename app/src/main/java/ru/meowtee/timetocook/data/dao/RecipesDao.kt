package ru.meowtee.timetocook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meowtee.timetocook.data.model.Receipt

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: List<Receipt>)

    @Query("SELECT * FROM receipt")
    fun getAllRecipes(): List<Receipt>

    @Query("SELECT * FROM receipt WHERE name LIKE '%' || :name  || '%'")
    fun getRecipesByName(name: String): List<Receipt>
}