package com.example.unidorm.model.dbModel

import android.os.Parcel
import android.os.Parcelable

data class DormitoryModel(
    var dormId: String? = null,
    var dormName: String? = null,
    var dormAddress: String? = null,
    var dormPhoto: String? = null,
    var passportTime: String? = null,
    var commendTime: String? = null,
    var castellanTime: String? = null,
    var showerTime:String? = null
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dormId)
        parcel.writeString(dormName)
        parcel.writeString(dormAddress)
        parcel.writeString(dormPhoto)
        parcel.writeString(passportTime)
        parcel.writeString(commendTime)
        parcel.writeString(castellanTime)
        parcel.writeString(showerTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DormitoryModel> {
        override fun createFromParcel(parcel: Parcel): DormitoryModel {
            return DormitoryModel(parcel)
        }

        override fun newArray(size: Int): Array<DormitoryModel?> {
            return arrayOfNulls(size)
        }
    }
}
