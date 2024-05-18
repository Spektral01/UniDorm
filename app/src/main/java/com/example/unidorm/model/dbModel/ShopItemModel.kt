package com.example.unidorm.model.dbModel

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.storage.StorageReference

data class ShopItemModel(
    var itemId: String? = null,
    var Item: String? = null,
    var Price: String? = null,
    var PersonName: String? = null,
    var PersonNumber: String? = null,
    var Description: String? = null,
    var Picture: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemId)
        parcel.writeString(Item)
        parcel.writeString(Price)
        parcel.writeString(PersonName)
        parcel.writeString(PersonNumber)
        parcel.writeString(Description)
        parcel.writeString(Picture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShopItemModel> {
        override fun createFromParcel(parcel: Parcel): ShopItemModel {
            return ShopItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ShopItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
