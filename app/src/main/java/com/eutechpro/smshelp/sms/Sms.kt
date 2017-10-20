package com.eutechpro.smshelp.sms

import android.os.Parcel
import android.os.Parcelable
import java.util.*
/*
    Android Extensions offers @Parcelize, but it has a bug (already reported: https://youtrack.jetbrains.com/issue/KT-19300).
    Until it is fixed, I will go with manual Parcelable implementation

 */
data class Sms(val number: Int, var date: Date, var message: String? = null):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readSerializable() as Date,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeSerializable(date)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sms> {
        override fun createFromParcel(parcel: Parcel): Sms {
            return Sms(parcel)
        }

        override fun newArray(size: Int): Array<Sms?> {
            return arrayOfNulls(size)
        }
    }
}