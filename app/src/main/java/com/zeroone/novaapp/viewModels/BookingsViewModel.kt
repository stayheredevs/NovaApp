package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.ActiveBookingsModel
import com.zeroone.novaapp.responseModels.PropertyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel @Inject constructor(): ViewModel() {

    var activeBookingLiveData: MutableLiveData<MutableList<ActiveBookingsModel>>? = null
        get() {
            if (field == null) {
                activeBookingLiveData = MutableLiveData()
            }
            return field
        }





    fun processActiveBookings(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val activeBookingsModel: Array<ActiveBookingsModel> = gson.fromJson(activeBookings, Array<ActiveBookingsModel>::class.java)
        val lstActiveBookings = activeBookingsModel.toMutableList()
        activeBookingLiveData?.postValue(lstActiveBookings)


    }




    val activeBookings = """[{
  "propertyId": "P12345",
  "propertyName": "Belsize court",
  "serviceType": "General Service",
  "status": "Started",
  "timestamp": "Today, 2:30 PM"
}]
"""



}