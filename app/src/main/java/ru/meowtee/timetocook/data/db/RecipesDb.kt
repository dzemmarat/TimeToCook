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
import ru.meowtee.timetocook.data.model.Ingredient

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
        const val DATABASE_VERSION = 2
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
                image = R.drawable.shakshuka,
                title = "Шакшука",
                isFavourite = false,
                portions = 2,
                time = 30,
                ingredients = listOf(
                    Ingredient(
                        name = "Оливковое масло",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Репчатый лук",
                        count = 0.5
                    ),
                    Ingredient(
                        name = "Чеснок",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Сладкий перец",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Помидоры",
                        count = 4.0
                    ),
                    Ingredient(
                        name = "Томатная паста",
                        count = 2.0
                    ),
                    Ingredient(
                        name = "Молотый кумин",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Паприка",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Молотый черный перец",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Сахар",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Соль",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Куриное яйцо",
                        count = 5.0
                    ),
                    Ingredient(
                        name = "Рубленная петрушка",
                        count = 0.0
                    ),
                )
            ),
            Receipt(
                image = R.drawable.ic_dish,
                title = "Пряный тыквенный суп",
                isFavourite = false,
                portions = 0,
                time = 30,
                ingredients = listOf(
                    Ingredient(
                        name = "",
                        count = 0.0
                    ),
                )
            ),
            Receipt(
                image = R.drawable.soup_cvetnaya_kapusta,
                title = "Суп-пюре из цветной капусты",
                isFavourite = false,
                portions = 6,
                time = 15,
                ingredients = listOf(
                    Ingredient(
                        name = "Молотый черный перец",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Соль",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Петрушка",
                        count = 20.0
                    ),
                    Ingredient(
                        name = "Сливки 35%-ные",
                        count = 100.0
                    ),
                    Ingredient(
                        name = "Сливочное масло",
                        count = 100.0
                    ),
                    Ingredient(
                        name = "Куриный бульон",
                        count = 0.5
                    ),
                    Ingredient(
                        name = "Молоко",
                        count = 0.5
                    ),
                    Ingredient(
                        name = "Лук-порей",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Цветная капуста",
                        count = 1.0
                    ),
                )
            ),
            Receipt(
                image = R.drawable.chiken_soup_mushroom,
                title = "Куриный суп с шампиньонами и зеленью",
                isFavourite = false,
                portions = 0,
                time = 30,
                ingredients = listOf(
                    Ingredient(
                        name = "",
                        count = 0.0
                    ),
                )
            ),
            Receipt(
                image = R.drawable.potato_graten,
                title = "Картофельный гратен",
                isFavourite = false,
                portions = 0,
                time = 30,
                ingredients = listOf(
                    Ingredient(
                        name = "",
                        count = 0.0
                    ),
                )
            ),
        )
    }
}