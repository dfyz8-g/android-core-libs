package com.cren90.android.common.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.ibrahimsn.library.LiveSharedPreferences
import javax.inject.Inject

@Suppress("unused", "OPT_IN_USAGE")
class EncryptedSharedPrefs @Inject constructor(context: Context) {
    private var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "${context.packageName}.ENCRYPTED_PREFERENCES",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val livePreferences = LiveSharedPreferences(sharedPreferences)
    private val flowPreferences = FlowSharedPreferences(sharedPreferences, Dispatchers.IO)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    /**
     * Gets the [String] for the given [key] or the null if not found
     */
    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    /**
     * Gets the [String] for the given [key] or the [defaultValue] if not found
     */
    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * Gets a [LiveData] that holds the current [String] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getStringAsLiveData(key: String, defaultValue: String): LiveData<String> =
        livePreferences.getString(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [String] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getStringAsFlow(key: String, defaultValue: String): Flow<String> =
        flowPreferences.getString(key, defaultValue).asFlow()

    /**
     * Sets the [String] for the given [key] to the [value] specified
     */
    fun setString(key: String, value: String?) {
        editor.putString(key, value).apply()
    }

    /**
     * Gets the [Boolean] for the given [key] or the null if not found
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getBoolean(key: String): Boolean? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getBoolean(
            key,
            false
        )
    }

    /**
     * Gets the [Boolean] for the given [key] or the [defaultValue] if not found
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean {
        return getBoolean(key) ?: defaultValue
    }

    /**
     * Gets a [LiveData] that holds the current [Boolean] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getBooleanAsLiveData(key: String, defaultValue: Boolean): LiveData<Boolean> =
        livePreferences.getBoolean(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [Boolean] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getBooleanAsFlow(key: String, defaultValue: Boolean): Flow<Boolean> =
        flowPreferences.getBoolean(key, defaultValue).asFlow()

    /**
     * Sets the [Boolean] for the given [key] to the [value] specified
     */
    fun setBoolean(key: String, value: Boolean?) {
        if (value == null) {
            editor.remove(key).apply()
        } else {
            editor.putBoolean(key, value).apply()
        }
    }

    /**
     * Gets the [Long] for the given [key] or the null if not found
     */
    fun getLong(key: String): Long? {
        return if(!sharedPreferences.contains(key)) null else sharedPreferences.getLong(key, 0)
    }

    /**
     * Gets the [Long] for the given [key] or the [defaultValue] if not found
     */
    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    /**
     * Gets a [LiveData] that holds the current [Long] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getLongAsLiveData(key: String, defaultValue: Long): LiveData<Long> =
        livePreferences.getLong(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [Long] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getLongAsFlow(key: String, defaultValue: Long): Flow<Long> =
        flowPreferences.getLong(key, defaultValue).asFlow()

    /**
     * Sets the [Long] for the given [key] to the [value] specified
     */
    fun setLong(key: String, value: Long?) {
        if (value == null) {
            editor.remove(key).apply()
        } else {
            editor.putLong(key, value).apply()
        }
    }

    /**
     * Gets the [Int] for the given [key] or the null if not found
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getInt(key: String): Int? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getInt(key, 0)
    }

    /**
     * Gets the [Int] for the given [key] or the [defaultValue] if not found
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return getInt(key) ?: defaultValue
    }

    /**
     * Gets a [LiveData] that holds the current [Int] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getIntAsLiveData(key: String, defaultValue: Int): LiveData<Int> =
        livePreferences.getInt(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [Int] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getIntAsFlow(key: String, defaultValue: Int): Flow<Int> =
        flowPreferences.getInt(key, defaultValue).asFlow()

    /**
     * Sets the [Int] for the given [key] to the [value] specified
     */
    fun setInt(key: String, value: Int?) {
        if (value == null) {
            editor.remove(key).apply()
        } else {
            editor.putInt(key, value).apply()
        }
    }

    /**
     * Gets the [Float] for the given [key] or the null if not found
     */
    fun getFloat(key: String): Float? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getFloat(key, 0f)
    }

    /**
     * Gets the [Float] for the given [key] or the [defaultValue] if not found
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    /**
     * Gets a [LiveData] that holds the current [Float] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getFloatAsLiveData(key: String, defaultValue: Float): LiveData<Float> =
        livePreferences.getFloat(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [Float] at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getFloatAsFlow(key: String, defaultValue: Float): Flow<Float> =
        flowPreferences.getFloat(key, defaultValue).asFlow()

    /**
     * Sets the [Float] for the given [key] to the [value] specified
     */
    fun setFloat(key: String, value: Float?) {
        if (value == null) {
            editor.remove(key).apply()
        } else {
            editor.putFloat(key, value).apply()
        }
    }


    /**
     * Gets the [Set]<[String]> for the given [key] or the null if not found
     */
    fun getStringSet(key: String): Set<String>? {
        return sharedPreferences.getStringSet(key, null)
    }

    /**
     * Gets the [Set]<[String]> for the given [key] or the [defaultValue] if not found
     */
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return sharedPreferences.getStringSet(key, defaultValue) ?: defaultValue
    }

    /**
     * Gets a [LiveData] that holds the current [Set]<[String]> at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getStringSetAsLiveData(key: String, defaultValue: Set<String>): LiveData<Set<String>> =
        livePreferences.getStringSet(key, defaultValue)

    /**
     * Gets a [Flow] that holds the current [Set]<[String]> at any given time for the given [key] or the [defaultValue] if not found
     */
    fun getStringSetAsFlow(key: String, defaultValue: Set<String>): Flow<Set<String>> =
        flowPreferences.getStringSet(key, defaultValue).asFlow()

    /**
     * Sets the [Set]<[String]> for the given [key] to the [value] specified
     */
    fun setStringSet(key: String, value: Set<String>) {
        editor.putStringSet(key, value).apply()
    }
}