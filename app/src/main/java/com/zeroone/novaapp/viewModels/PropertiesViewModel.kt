package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.PropertyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PropertiesViewModel @Inject constructor(): ViewModel() {

    var propertiesLiveData: MutableLiveData<MutableList<PropertyModel>>? = null
        get() {
            if (field == null) {
                propertiesLiveData = MutableLiveData()
            }
            return field
        }


    fun processProperties(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()


        val propertiesModel: Array<PropertyModel> = gson.fromJson(properties, Array<PropertyModel>::class.java)
        val lstProperties = propertiesModel.toMutableList()
        propertiesLiveData?.postValue(lstProperties)

    }

    val properties = """[
  {
    "propertyId": "P001",
    "propertyName": "Sunset Apartments",
    "bedrooms": 2,
    "size": "85 sqm"
  },
  {
    "propertyId": "P002",
    "propertyName": "Greenwood Villas",
    "bedrooms": 4,
    "size": "220 sqm"
  },
  {
    "propertyId": "P003",
    "propertyName": "Hillview Estate",
    "bedrooms": 3,
    "size": "150 sqm"
  },
  {
    "propertyId": "P004",
    "propertyName": "Palm Crest Homes",
    "bedrooms": 5,
    "size": "300 sqm"
  },
  {
    "propertyId": "P005",
    "propertyName": "Riverbend Court",
    "bedrooms": 1,
    "size": "65 sqm"
  },
  {
    "propertyId": "P006",
    "propertyName": "Lakeside Heights",
    "bedrooms": 3,
    "size": "170 sqm"
  },
  {
    "propertyId": "P007",
    "propertyName": "Silver Oaks Residence",
    "bedrooms": 2,
    "size": "110 sqm"
  },
  {
    "propertyId": "P008",
    "propertyName": "Urban Ridge Apartments",
    "bedrooms": 1,
    "size": "75 sqm"
  },
  {
    "propertyId": "P009",
    "propertyName": "Meadow Park Estate",
    "bedrooms": 4,
    "size": "250 sqm"
  },
  {
    "propertyId": "P010",
    "propertyName": "Crystal Towers",
    "bedrooms": 3,
    "size": "160 sqm"
  }
]
"""
}