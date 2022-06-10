package ru.meowtee.timetocook.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.core.extensions.ioThread
import ru.meowtee.timetocook.data.converter.ListsConverter
import ru.meowtee.timetocook.data.db.RecipesDb.Companion.DATABASE_VERSION
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.data.dao.RecipesDao

@Database(
    entities = [
        Receipt::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(ListsConverter::class)
abstract class RecipesDb : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Recipes-Room"

        @Volatile private var INSTANCE: RecipesDb? = null

        fun getInstance(context: Context): RecipesDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataSource(context).also { INSTANCE = it }
            }

        private fun buildDataSource(context: Context): RecipesDb =
            Room.databaseBuilder(context.applicationContext, RecipesDb::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).recipesDao().addRecipe(PREPOPULATE_DATA)
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()

        private val PREPOPULATE_DATA = listOf(
            Receipt(
                image = R.drawable.ic_dish_2,
                name = "Французскиe круасаны с шоколадным соусом",
                isFavourite = false,
                tags = emptyList()
            ),
            Receipt(
                image = R.drawable.ic_dish,
                name = "Пряный тыквенный суп",
                isFavourite = false,
                tags = emptyList()
            ),
            Receipt(
                image = R.drawable.soup_cvetnaya_kapusta,
                name = "Суп-пюре из цветной капусты",
                isFavourite = false,
                tags = emptyList()
            ),
            Receipt(
                image = R.drawable.chiken_soup_mushroom,
                name = "Куриный суп с шампиньонами и зеленью",
                isFavourite = false,
                tags = emptyList()
            ),
            Receipt(
                image = R.drawable.potato_graten,
                name = "Картофельный гратен",
                isFavourite = false,
                tags = emptyList()
            ),
        )
    }
}