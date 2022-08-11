/**
 * Created by Chris Renfrow on 4/21/20.
 */

package com.cren90.android.time

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class WeekDay {
    @SerializedName("Sunday")
    @Json(name = "Sunday")
    SUNDAY,

    @SerializedName("Monday")
    @Json(name = "Monday")
    MONDAY,

    @SerializedName("Tuesday")
    @Json(name = "Tuesday")
    TUESDAY,

    @SerializedName("Wednesday")
    @Json(name = "Wednesday")
    WEDNESDAY,

    @SerializedName("Thursday")
    @Json(name = "Thursday")
    THURSDAY,

    @SerializedName("Friday")
    @Json(name = "Friday")
    FRIDAY,

    @SerializedName("Saturday")
    @Json(name = "Saturday")
    SATURDAY

}