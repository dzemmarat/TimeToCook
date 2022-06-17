package ru.meowtee.timetocook.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: Int = 0,

    @ColumnInfo(name = "time")
    val time: Int = 0,

    @ColumnInfo(name = "name")
    val title: String = "",

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient> = emptyList(),

    @ColumnInfo(name = "portions")
    val portions: Int = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.createTypedArrayList(Ingredient)!!,
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(image)
        parcel.writeInt(time)
        parcel.writeString(title)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeTypedList(ingredients)
        parcel.writeInt(portions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Receipt> {
        override fun createFromParcel(parcel: Parcel): Receipt {
            return Receipt(parcel)
        }

        override fun newArray(size: Int): Array<Receipt?> {
            return arrayOfNulls(size)
        }
    }
}