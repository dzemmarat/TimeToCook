package ru.meowtee.timetocook.data.db

import android.content.ContentValues
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.core.extensions.ioThread
import ru.meowtee.timetocook.data.converter.ListsConverter
import ru.meowtee.timetocook.data.dao.RecipesDao
import ru.meowtee.timetocook.data.dao.RecommendationsDao
import ru.meowtee.timetocook.data.db.RecipesDb.Companion.DATABASE_VERSION
import ru.meowtee.timetocook.data.model.Ingredient
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.data.model.Recommendation

@Database(
    entities = [
        Receipt::class,
        Recommendation::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(ListsConverter::class)
abstract class RecipesDb : RoomDatabase() {
    abstract fun recommendationsDao(): RecommendationsDao
    abstract fun recipesDao(): RecipesDao

    companion object {
        const val DATABASE_VERSION = 8
        private const val DATABASE_NAME = "Recipes-Room"

        @Volatile
        private var INSTANCE: RecipesDb? = null

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
                            PREPOPULATE_DATA_RECEIPTS.forEach {
                                getInstance(context).recipesDao().addRecipe(it)
                            }
                            PREPOPULATE_DATA_RECOMMENDATIONS.forEach {
                                getInstance(context).recommendationsDao().addRecommendation(it)
                            }
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        var recipes: List<Receipt>
                        var recommendations: List<Recommendation>
                        ioThread {
                            recipes = getInstance(context).recipesDao().getAllRecipes()
                            if (recipes.isEmpty()) {
                                PREPOPULATE_DATA_RECEIPTS.forEach {
                                    getInstance(context).recipesDao().addRecipe(it)
                                }
                            }

                            recommendations = getInstance(context).recommendationsDao().getAllRecommendations()
                            if (recommendations.isEmpty()) {
                                PREPOPULATE_DATA_RECOMMENDATIONS.forEach {
                                    getInstance(context).recommendationsDao().addRecommendation(it)
                                }
                            }
                        }
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        runBlocking(Dispatchers.IO) {
                            getInstance(context).clearAllTables()
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()

        private val PREPOPULATE_DATA_RECEIPTS = listOf(
            Receipt(
                image = R.drawable.shakshuka,
                title = "Шакшука",
                isFavourite = false,
                portions = 2,
                rating = 3,
                time = "30 мин.",
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
                ),
                steps = listOf(
                    "Разогрейте оливковое масло в глубокой сковороде. Добавьте лук, пассеруйте до прозрачности, добавьте чеснок и томите еще минуту-две",
                    "Добавьте порезанный болгарский перец – и готовьте еще 5–7 минут, после чего бросьте в сковороду помидоры и томатную пасту, и перемешайте. Теперь приправьте специями и сахаром. Снова перемешайте и снова готовьте 5–7 минут. Посолите, поперчите и добавьте приправы по вкусу",
                    "По одному выпустите в сковороду яйца, накройте крышкой и готовьте 10–15 минут",
                    "За это время соус чуть выпарится. Но следите за тем, чтобы он не выпарился совсем – иначе шакшука подгорит"
                )
            ),
            Receipt(
                image = R.drawable.ic_dish,
                title = "Пряный тыквенный суп",
                isFavourite = false,
                portions = 0,
                time = "30 мин.",
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
                time = "15 мин",
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
                time = "30 мин.",
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
                time = "30 мин.",
                ingredients = listOf(
                    Ingredient(
                        name = "",
                        count = 0.0
                    ),
                )
            ),
        )
        val PREPOPULATE_DATA_RECOMMENDATIONS = listOf(
            Recommendation(
                title = "Хорошо прогревайте сковороду",
                description = "Если не прогревать сковороду, то продукты будут либо пригорать, либо больше тушиться. \n" +
                        "Капните на разогретую сковороду воды и если она мгновенно испарилась, то сковорода достаточно горячая"
            ),
            Recommendation(
                title = "Варите овощи по таймеру",
                description = "Речь идет о брокколи, спарже и других зеленых овощах. \n" +
                        "Необходимо варить 3-7 минут в кипящей воде, а затем – самое важное – переложить овощи в кастрюлю с ледяной водой. Т.е.  “бланшировать” овощи. "
            ),
            Recommendation(
                title = "Тушите на медленном огне",
                description = "Жидкости должно быть столько, чтобы покрывать ингредиенты не более чем на половину.\n" +
                        "После этого убавьте “огонь” на плите. Мелкие пузырьки могут появляться не чаще раза в 2-3 секунды, иначе уменьшите огонь"
            ),
        )
    }
}