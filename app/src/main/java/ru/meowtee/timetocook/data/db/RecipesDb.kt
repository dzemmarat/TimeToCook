package ru.meowtee.timetocook.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
        const val DATABASE_VERSION = 11
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

                            recommendations =
                                getInstance(context).recommendationsDao().getAllRecommendations()
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
                type = "Основное блюдо",
                timeTag = "За 30 минут",
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
                difficult = "Простой",
                steps = mutableListOf(
                    "Разогрейте оливковое масло в глубокой сковороде. Добавьте лук, пассеруйте до прозрачности, добавьте чеснок и томите еще минуту-две",
                    "Добавьте порезанный болгарский перец – и готовьте еще 5–7 минут, после чего бросьте в сковороду помидоры и томатную пасту, и перемешайте. Теперь приправьте специями и сахаром. Снова перемешайте и снова готовьте 5–7 минут. Посолите, поперчите и добавьте приправы по вкусу",
                    "По одному выпустите в сковороду яйца, накройте крышкой и готовьте 10–15 минут",
                    "За это время соус чуть выпарится. Но следите за тем, чтобы он не выпарился совсем – иначе шакшука подгорит"
                )
            ),
            Receipt(
                image = R.drawable.kobler,
                title = "Коблер",
                portions = 6,
                time = "40 мин.",
                type = "Завтрак",
                timeTag = "За 1 час",
                ingredients = listOf(
                    Ingredient(
                        name = "Яблоки",
                        count = 5.0
                    ),
                    Ingredient(
                        name = "Финики",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Мед",
                        count = 2.0
                    ),
                    Ingredient(
                        name = "Лимонный сок",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Ваниль",
                        count = 0.5
                    ),
                    Ingredient(
                        name = "Соль",
                        count = 0.25
                    ),
                    Ingredient(
                        name = "Молотый имбирь",
                        count = 0.25
                    ),
                    Ingredient(
                        name = "Орехи",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Кокосовый масло",
                        count = 1.0
                    ),
                ),
                steps = mutableListOf(
                    "Яблоки очистить, вынуть сердцевину и мелко порезать",
                    "Орехи перемолоть в комбайне. Добавить соль, ваниль, кокосовое масло и четверть стакана фиников. Перемолоть еще раз. Отдельно перемолоть оставшиеся финики со всеми остальными ингредиентами",
                    "В формы для запекания (лучше использовать порционные) выложить яблоки, затем — смесь фиников с медом, лимонным соком и имбирем. Перемешать. Сверху закрыть смесью с молотыми орехами",
                    "Запекать в духовке при 200 градусов около 30 минут до появления золотистого цвета"
                )
            ),
            Receipt(
                image = R.drawable.soup_cvetnaya_kapusta,
                title = "Суп-пюре из цветной капусты",
                isFavourite = false,
                portions = 6,
                time = "15 мин",
                difficult = "Продвинутый",
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
                ),
                steps = mutableListOf(
                    "Цветную капусту разобрать на соцветия, самые маленькие из них сварить целиком в подсоленной воде, а крупные порезать на более мелкие части",
                    "Как только маленькие соцветия сварятся, надо их тут же остудить, чтобы сохранить состояние аль-денте",
                    "Параллельно надо растопить в сотейнике или кастрюле сливочное масло и обжарить на нем мелкорубленый порей (только белую часть), не забывая все время перемешивать, чтобы не дать луку потемнеть",
                    "Влить в кастрюлю молоко, добавить нарезанные соцветия капусты и оставить вариться на небольшом огне в течение пяти минут. Молоко при этом не должно бурно кипеть, а лишь спокойно побулькивать. Через пять минут влить в кастрюлю горячий куриный бульон и варить, пока капуста не станет совсем мягкой",
                    "Снять с огня. Посолить, поперчить по вкусу, добавить мелко нарезанную петрушку, сливки и прокрутить суп в блендере. Подавать в глубокой тарелке, положив в нее маленькие похрустывающие соцветия цветной капусты и немного красной икры"
                )
            ),
            Receipt(
                image = R.drawable.tvor_zap,
                title = "Творожная запеканка в микроволновке",
                isFavourite = false,
                portions = 2,
                time = "10 мин.",
                difficult = "Простой",
                ingredients = listOf(
                    Ingredient(
                        name = "Творог",
                        count = 220.0
                    ),
                    Ingredient(
                        name = "Овсяные хлопья",
                        count = 30.0
                    ),
                    Ingredient(
                        name = "Куриное яйцо",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Мед",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Курага",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Корица",
                        count = 0.0
                    ),
                ),
                steps = mutableListOf(
                    "Смешайте в миске творог, яйцо, мед и овсяные хлопья до однородной массы. Мед можно заменить сахаром или сахарозаменителем",
                    "Добавьте нарезанную курагу и корицу. Перемешайте. При желании можно использовать другие сухофрукты и немного орехов",
                    "Поставьте готовую массу в микроволновку на 5 минут"
                )
            ),
            Receipt(
                image = R.drawable.potato_graten,
                title = "Картофельный гратен",
                isFavourite = false,
                portions = 8,
                time = "60 мин.",
                ingredients = listOf(
                    Ingredient(
                        name = "Картофель",
                        count = 3.0
                    ),
                    Ingredient(
                        name = "Молоко",
                        count = 250.0
                    ),
                    Ingredient(
                        name = "Вода",
                        count = 250.0
                    ),
                    Ingredient(
                        name = "Чеснок",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Сливочное масло",
                        count = 50.0
                    ),
                    Ingredient(
                        name = "Молотый черный перец",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Твердый сыр",
                        count = 70.0
                    ),
                    Ingredient(
                        name = "Соль",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Мускатный орех",
                        count = 0.0
                    ),
                    Ingredient(
                        name = "Сметана",
                        count = 150.0
                    ),
                ),
                steps = mutableListOf(
                    "В маленькой кастрюльке соединить молоко с водой, посолить, поперчить и добавить порубленный зубчик чеснока. Довести до кипения",
                    "В это время помыть и почистить картофель. Порезать кружками толщиной 4–5 мм",
                    "Отправить картофель в кипящее молоко, чуть снизить температуру и варить 10–15 минут. Следить, чтобы кружки не разваливались. Слить картофельную воду",
                    "Форму для запекания смазать сливочным маслом. Выложить картофель. Смазать сметаной. Сверху выложить кусочки сливочного масла и посыпать сыром",
                    "Отправить в духовку на 40 минут и запекаться при 180 градусах"
                )
            ),
            Receipt(
                image = R.drawable.ruletiki,
                title = "Рулетики из баклажанов",
                isFavourite = false,
                difficult = "Простой",
                portions = 2,
                time = "20 мин.",
                ingredients = listOf(
                    Ingredient(
                        name = "Баклажаны",
                        count = 1.0
                    ),
                    Ingredient(
                        name = "Сыр",
                        count = 200.0
                    ),
                    Ingredient(
                        name = "Чеснок",
                        count = 3.0
                    ),
                    Ingredient(
                        name = "Майонез",
                        count = 100.0
                    ),
                    Ingredient(
                        name = "Помидоры",
                        count = 1.0
                    ),
                ),
                steps = mutableListOf(
                    "У баклажана отрезаем плодоножку и нарезаем вдоль на пластины. Обжариваем с двух сторон до готовности",
                    "Делаем начинку. Для этого смешиваем майонез, тертый сыр и чеснок",
                    "Каждую пластинку смазываем начинкой, кладем ломтик помидора и сворачиваем в рулет"
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