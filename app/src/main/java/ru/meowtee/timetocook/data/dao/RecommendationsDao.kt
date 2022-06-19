package ru.meowtee.timetocook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meowtee.timetocook.data.model.Recommendation

@Dao
interface RecommendationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecommendation(recommendation: Recommendation)

    @Query("SELECT * FROM recommendation")
    fun getAllRecommendations(): List<Recommendation>
}