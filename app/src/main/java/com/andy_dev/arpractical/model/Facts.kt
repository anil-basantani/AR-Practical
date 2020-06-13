package com.andy_dev.arpractical.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andy_dev.arpractical.BaseApplication
import com.andy_dev.arpractical.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "facts")
data class Facts(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title") @SerializedName("title") @Expose var title: String? = "",
    @ColumnInfo(name = "description") @SerializedName("description") @Expose var description: String? = "",
    @ColumnInfo(name = "imageHref") @SerializedName("imageHref") @Expose var imageHref: String? = ""
) : Serializable {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
//            if (!imageUrl.isNullOrEmpty())
            Utility.loadImage(BaseApplication.getApplicationContext(), imageUrl ?: "", view)
        }
    }
}