package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.AssetCategoriesModel
import com.zeroone.novaapp.responseModels.ServiceOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UtilitiesViewModel @Inject constructor(): ViewModel() {

    var assetsCategoriesLiveData: MutableLiveData<MutableList<AssetCategoriesModel>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }

    fun processCategories(category: String){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        when(category){
            "assets"->{
                val categoriesArray: Array<AssetCategoriesModel> = gson.fromJson(assetsCategories, Array<AssetCategoriesModel>::class.java)
                assetsCategoriesLiveData?.postValue(categoriesArray.toMutableList())

            }
            "history"->{
                val categoriesArray: Array<AssetCategoriesModel> = gson.fromJson(historyCategories, Array<AssetCategoriesModel>::class.java)
                assetsCategoriesLiveData?.postValue(categoriesArray.toMutableList())
            }
        }



    }


    val assetsCategories = """[
  {
    "CategoryID": "1",
    "CategoryName": "My Properties"
  },
  {
    "CategoryID": "2",
    "CategoryName": "My Managers"
  }
]"""

    val historyCategories = """[
        {
    "CategoryID": "1",
    "CategoryName": "Current Jobs"
  },
  {
    "CategoryID": "2",
    "CategoryName": "Past Jobs"
  },
  {
    "CategoryID": "3",
    "CategoryName": "Invoices"
  }
]"""
}