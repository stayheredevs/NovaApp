package com.zeroone.novaapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zeroone.novaapp.responseModels.BookingDetails
import com.zeroone.novaapp.responseModels.PropertyModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(@ApplicationContext context: Context) {

    private val app_prefs: SharedPreferences

    private val context: Context

    init {
        app_prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        this.context = context
    }

    var eventSource: String?
        get() = app_prefs.getString("event_source", "")
        set(eventSource) {
            app_prefs.edit { putString("event_source", eventSource) }
        }

    var propertyObject: String?
        get() = app_prefs.getString("selected_property", "")
        set(value) {
            app_prefs.edit { putString("selected_property", value) }
        }



    var selectedProperty: MutableList<PropertyModel>?
        get() {
            val gson = Gson()
            val json: String = app_prefs.getString("property_list", "")!!

            val type = object : TypeToken<MutableList<PropertyModel?>?>() {}.getType()
            return gson.fromJson<MutableList<PropertyModel?>?>(json, type) as MutableList<PropertyModel>?
        }
        set(value) {

            val gson = Gson()
            val json = gson.toJson(value)

            app_prefs.edit {
                putString("property_list", json)
            }

        }

    var selectedService: MutableList<BookingDetails>?
        get() {
            val gson = Gson()
            val json: String = app_prefs.getString("selected_service_list", "")!!

            val type = object : TypeToken<MutableList<BookingDetails?>?>() {}.getType()
            return gson.fromJson<MutableList<BookingDetails?>?>(json, type) as MutableList<BookingDetails>?
        }
        set(value) {

            val gson = Gson()
            val json = gson.toJson(value)

            app_prefs.edit {
                putString("selected_service_list", json)
            }

        }


    var isFirstTimeLaunch: Boolean
        get() = app_prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            val editor = app_prefs.edit()
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    var isAgentLoggedIn: Boolean?
        get() = app_prefs.getBoolean("is_logged_in", false)
        set(logged_in) {
            val edit = app_prefs.edit()
            edit.putBoolean("is_logged_in", logged_in!!)
            edit.apply()
        }


    companion object {
        const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

        fun removeSpecialCharacters(input: String): String {
            return input.trim('"').replace("\\\"", "\"").replace("\\r\\n", "").replace("\\", "")
        }
    }


}