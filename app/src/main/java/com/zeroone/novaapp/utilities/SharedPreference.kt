package com.zeroone.novaapp.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zeroone.novaapp.responseModels.BookingDetails
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.responseModels.ServicesModel
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


}