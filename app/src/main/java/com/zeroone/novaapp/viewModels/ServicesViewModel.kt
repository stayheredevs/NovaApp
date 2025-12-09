package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.home.InvoicesModel
import com.zeroone.novaapp.responseModels.PastJobsModel
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

    var pastJobsLiveData: MutableLiveData<MutableList<PastJobsModel>>? = null
        get() {
            if (field == null) {
                pastJobsLiveData = MutableLiveData()
            }
            return field
        }

    var invoicesLiveData: MutableLiveData<MutableList<InvoicesModel>>? = null
        get() {
            if (field == null) {
                invoicesLiveData = MutableLiveData()
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

    fun processPastJobs() {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val pastJobsArray: Array<PastJobsModel> = gson.fromJson(pastJobs, Array<PastJobsModel>::class.java)
        pastJobsLiveData?.postValue(pastJobsArray.toMutableList())
    }

    fun processInvoices() {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val invoicesArray: Array<InvoicesModel> = gson.fromJson(invoices, Array<InvoicesModel>::class.java)
        invoicesLiveData?.postValue(invoicesArray.toMutableList())
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

    var pastJobs = """[
  {
    "propertyId": "P-10231",
    "propertyName": "Sunset Villas Apartment",
    "serviceType": "General Cleaning",
    "serviceDate": "2025-02-14",
    "serviceTime": "09:30 AM"
  },
  {
    "propertyId": "P-87452",
    "propertyName": "Greenwood Estate House 12",
    "serviceType": "Deep Cleaning",
    "serviceDate": "2025-03-02",
    "serviceTime": "01:45 PM"
  },
  {
    "propertyId": "P-66317",
    "propertyName": "Riverside Heights Block B",
    "serviceType": "Sofa Cleaning",
    "serviceDate": "2025-01-28",
    "serviceTime": "11:10 AM"
  },
  {
    "propertyId": "P-44892",
    "propertyName": "Parkview Towers Unit 7A",
    "serviceType": "Carpet Cleaning",
    "serviceDate": "2025-02-05",
    "serviceTime": "03:20 PM"
  },
  {
    "propertyId": "P-99104",
    "propertyName": "Oakridge Residency",
    "serviceType": "Mattress Cleaning",
    "serviceDate": "2025-03-12",
    "serviceTime": "10:05 AM"
  }
]
"""

    val invoices = """[
  {
    "invoiceNumber": "INV-2025001",
    "invoiceStatus": "cleared"
  },
  {
    "invoiceNumber": "INV-2025002",
    "invoiceStatus": "cleared"
  },
  {
    "invoiceNumber": "INV-2025003",
    "invoiceStatus": "cleared"
  },
  {
    "invoiceNumber": "INV-2025004",
    "invoiceStatus": "cleared"
  },
  {
    "invoiceNumber": "INV-2025005",
    "invoiceStatus": "cleared"
  }
]
"""

}