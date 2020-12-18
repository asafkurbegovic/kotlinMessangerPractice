package com.asaf.kotlinmessanger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val username: String, val profilePicUrl: String, val uid: String): Parcelable{
    constructor(): this("","","")
}
