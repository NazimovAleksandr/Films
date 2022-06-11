package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MovieItem(
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

    companion object CREATOR : Parcelable.Creator<MovieItem> {
        override fun createFromParcel(parcel: Parcel): MovieItem {
            return (parcel.readSerializable() as MovieItem)
        }

        override fun newArray(size: Int): Array<MovieItem?> {
            return arrayOfNulls(size)
        }
    }
}