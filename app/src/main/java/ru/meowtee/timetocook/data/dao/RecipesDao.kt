package ru.meowtee.timetocook.data.dao

import androidx.room.*
import ru.meowtee.timetocook.data.model.Receipt

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: Receipt)

    @Query("SELECT * FROM receipt")
    fun getAllRecipes(): List<Receipt>

    @Query("SELECT * FROM receipt WHERE name LIKE '%' || :name  || '%'")
    fun getRecipesByName(name: String): List<Receipt>

    @Query("SELECT * FROM receipt WHERE isFavourite = :isFavourite")
    fun getRecipesByFavourite(isFavourite: Boolean): List<Receipt>

    @Update
    fun updateReceipt(receipt: Receipt)

    @Query("SELECT * FROM receipt ORDER BY RANDOM() LIMIT 6")
    fun getRandomReceipts(): List<Receipt>

    @Query("SELECT * FROM receipt WHERE difficult = :difficult")
    fun getRecipesByDifficult(difficult: String) : List<Receipt>
}