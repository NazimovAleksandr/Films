package com.nazimovaleksandr.films.single_activity.data.entities.ui

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MovieUI(
    val image: Int,
    val name: String,
    val details: String,
    var isFavorite: Boolean = false,
    var isViewed: Boolean = false,
    var comment: String = ""
) : Serializable, Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeSerializable(this)
    }

    companion object CREATOR : Parcelable.Creator<MovieUI> {
        override fun createFromParcel(parcel: Parcel): MovieUI {
            return (parcel.readSerializable() as MovieUI)
        }

        override fun newArray(size: Int): Array<MovieUI?> {
            return arrayOfNulls(size)
        }
    }
}