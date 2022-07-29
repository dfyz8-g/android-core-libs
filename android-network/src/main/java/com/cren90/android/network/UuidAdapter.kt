/**
 * Created by Chris Renfrow on 6/26/20.
 */

package com.cren90.android.network

import com.smartrent.common.extension.toUUID
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UuidAdapter {
    @FromJson
    fun fromJson(json: String): UUID {
        return json.toUUID()
    }

    @ToJson
    fun toJson(uuid: UUID): String {
        return uuid.toString()
    }
}