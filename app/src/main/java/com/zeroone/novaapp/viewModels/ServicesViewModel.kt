package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.ServiceOptions
import com.zeroone.novaapp.responseModels.ServicesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(): ViewModel() {

    var servicesLiveData: MutableLiveData<MutableList<ServicesModel>>? = null
        get() {
            if (field == null) {
                servicesLiveData = MutableLiveData()
            }
            return field
        }

    var serviceOptionsLiveData: MutableLiveData<MutableList<ServiceOptions>>? = null
        get() {
            if (field == null) {
                serviceOptionsLiveData = MutableLiveData()
            }
            return field
        }


    fun processServices(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()


        val servicesArray: Array<ServicesModel> = gson.fromJson(services, Array<ServicesModel>::class.java)
        servicesLiveData?.postValue(servicesArray.toMutableList())

    }

    fun processServiceOptions(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val serviceOptionsArray: Array<ServiceOptions> = gson.fromJson(serviceOptions, Array<ServiceOptions>::class.java)
        serviceOptionsLiveData?.postValue(serviceOptionsArray.toMutableList())


    }

    val services = """[
  {
    "serviceTypeId": "1",
    "serviceTypeName": "General Cleaning",
    "iconUrl": "https://iconscout.com/icon/cleaning-service-1/png/64"  
  },
  {
    "serviceTypeId": "2",
    "serviceTypeName": "Deep Cleaning",
    "iconUrl": "https://iconscout.com/icon/cleaning-service-2/png/64"
  },
  {
    "serviceTypeId": "3",
    "serviceTypeName": "Sofa Cleaning",
    "iconUrl": "https://iconscout.com/icon/sofa-cleaning/png/64"
  },
  {
    "serviceTypeId": "4",
    "serviceTypeName": "Carpet Cleaning",
    "iconUrl": "https://iconscout.com/icon/carpet-cleaning/png/64"
  },
  {
    "serviceTypeId": "5",
    "serviceTypeName": "Mattress Cleaning",
    "iconUrl": "https://iconscout.com/icon/mattress-cleaning/png/64"
  },
  {
    "serviceTypeId": "6",
    "serviceTypeName": "Furniture Cleaning",
    "iconUrl": "https://iconscout.com/icon/furniture-cleaning/png/64"
  }
]
"""

    val serviceOptions = """[
  {
    "optionName": "3 Seater Sofa",
    "optionPrice": "1500"
  },
  {
    "optionName": "4 Seater Sofa",
    "optionPrice": "2000"
  },
  {
    "optionName": "5 Seater Sofa",
    "optionPrice": "2500"
  },
  {
    "optionName": "6 Seater Sofa",
    "optionPrice": "3000"
  },
  {
    "optionName": "7 Seater Sofa",
    "optionPrice": "3500"
  },
  {
    "optionName": "L-Shaped Sofa (Small)",
    "optionPrice": "3000"
  },
  {
    "optionName": "L-Shaped Sofa (Large)",
    "optionPrice": "4000"
  },
  {
    "optionName": "Recliner Sofa",
    "optionPrice": "2500"
  }
]
"""

}