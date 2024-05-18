package com.example.unidorm.model.dbModel

import android.os.Parcel
import android.os.Parcelable

data class NotificationModel(
    var notifId: String? = null,
    var notifText: String? = null,
    var discription: String? = null

): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(notifId)
        parcel.writeString(notifText)
        parcel.writeString(discription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotificationModel> {
        override fun createFromParcel(parcel: Parcel): NotificationModel {
            return NotificationModel(parcel)
        }

        override fun newArray(size: Int): Array<NotificationModel?> {
            return arrayOfNulls(size)
        }
    }
}
