package com.zeroone.novaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.zeroone.novaapp.responseModels.ActionsModel
import com.zeroone.novaapp.responseModels.AllocatedPropertiesModel
import com.zeroone.novaapp.responseModels.PropertyManagersModel
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

    var propertyManagersLiveData: MutableLiveData<MutableList<PropertyManagersModel>>? = null
        get() {
            if (field == null) {
                propertyManagersLiveData = MutableLiveData()
            }
            return field
        }

    var actionsLiveData: MutableLiveData<MutableList<ActionsModel>>? = null
        get() {
            if (field == null) {
                actionsLiveData = MutableLiveData()
            }
            return field
        }

    var allocatedPropertiesLiveData: MutableLiveData<MutableList<AllocatedPropertiesModel>>? = null
        get() {
            if (field == null) {
                allocatedPropertiesLiveData = MutableLiveData()
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

    fun processManagers() {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val managersModel: Array<PropertyManagersModel> = gson.fromJson(managers, Array<PropertyManagersModel>::class.java)
        val lstManagers = managersModel.toMutableList()
        propertyManagersLiveData?.postValue(lstManagers)

    }

    fun processAllocatedManagers(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val allocatedManagersModel: Array<PropertyManagersModel> = gson.fromJson(allocated_manager, Array<PropertyManagersModel>::class.java)
        val lstAllocatedManagers = allocatedManagersModel.toMutableList()
        propertyManagersLiveData?.postValue(lstAllocatedManagers)

    }

    fun processActions(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val actionsModel: Array<ActionsModel> = gson.fromJson(actions, Array<ActionsModel>::class.java)
        val lstActions = actionsModel.toMutableList()
        actionsLiveData?.postValue(lstActions)

    }

    fun processAllocatedProperties(){
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val propertiesModel: Array<AllocatedPropertiesModel> = gson.fromJson(allocatedProperties, Array<AllocatedPropertiesModel>::class.java)
        val lstProperties = propertiesModel.toMutableList()
        allocatedPropertiesLiveData?.postValue(lstProperties)



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

    var managers = """[
  {
    "managerId": "M001",
    "managerName": "John Mwangi",
    "propertiesManaging": 12,
    "phoneNumber": "+254712345678",
    "profilePicture": "https://randomuser.me/api/portraits/men/45.jpg"
  },
  {
    "managerId": "M002",
    "managerName": "Sarah Njeri",
    "propertiesManaging": 8,
    "phoneNumber": "+254798112233",
    "profilePicture": "https://randomuser.me/api/portraits/women/32.jpg"
  },
  {
    "managerId": "M003",
    "managerName": "David Kamau",
    "propertiesManaging": 15,
    "phoneNumber": "+254701556677",
    "profilePicture": "https://randomuser.me/api/portraits/men/12.jpg"
  },
  {
    "managerId": "M004",
    "managerName": "Linda Atieno",
    "propertiesManaging": 6,
    "phoneNumber": "+254743987654",
    "profilePicture": "https://randomuser.me/api/portraits/women/55.jpg"
  },
  {
    "managerId": "M005",
    "managerName": "Brian Otieno",
    "propertiesManaging": 20,
    "phoneNumber": "+254710224466",
    "profilePicture": "https://randomuser.me/api/portraits/men/77.jpg"
  }
]
"""


    val allocated_manager = """[
  {
    "managerName": "James Mwangi",
    "phoneNumber": "+254712345678",
    "profilePicture": "https://randomuser.me/api/portraits/men/45.jpg",
    "status": "Active",
    "allocationDate": "2024-02-12"
  },
  {
    "managerName": "Caroline Achieng",
    "phoneNumber": "+254798112233",
    "profilePicture": "https://randomuser.me/api/portraits/women/32.jpg",
    "status": "Suspended",
    "allocationDate": "2023-11-28"
  },
  {
    "managerName": "Brian Otieno",
    "phoneNumber": "+254701556677",
    "profilePicture": "https://randomuser.me/api/portraits/men/12.jpg",
    "status": "Active",
    "allocationDate": "2024-01-05"
  },
  {
    "managerName": "Lucy Wanjiru",
    "phoneNumber": "+254743987654",
    "profilePicture": "https://randomuser.me/api/portraits/women/55.jpg",
    "status": "Active",
    "allocationDate": "2024-03-01"
  },
  {
    "managerName": "Peter Kamau",
    "phoneNumber": "+254710224466",
    "profilePicture": "https://randomuser.me/api/portraits/men/77.jpg",
    "status": "Suspended",
    "allocationDate": "2023-09-17"
  }
]
"""

    var actions = """[
  {
    "action": "Call Manager"
  },
  {
    "action": "Suspend manager"
  }
]"""

    val allocatedProperties = """[
  {
    "propertyPicture": "https://images.unsplash.com/photo-1568605114967-8130f3a36994",
    "propertyName": "Sunset Villas",
    "propertyAddress": "Westlands, Nairobi",
    "unitNumber": "A-12"
  },
  {
    "propertyPicture": "https://images.unsplash.com/photo-1572120360610-d971b9d7767c",
    "propertyName": "Greenside Apartments",
    "propertyAddress": "Kileleshwa, Nairobi",
    "unitNumber": "B-04"
  },
  {
    "propertyPicture": "https://images.unsplash.com/photo-1600585154340-be6161a56a0c",
    "propertyName": "Oakridge Residency",
    "propertyAddress": "Riverside Drive, Nairobi",
    "unitNumber": "C-21"
  },
  {
    "propertyPicture": "https://images.unsplash.com/photo-1599423300746-b62533397364",
    "propertyName": "Pearl Homes",
    "propertyAddress": "Lavington, Nairobi",
    "unitNumber": "D-09"
  },
  {
    "propertyPicture": "https://images.unsplash.com/photo-1580587771525-78b9dba3b914",
    "propertyName": "Hillcrest Towers",
    "propertyAddress": "Kilimani, Nairobi",
    "unitNumber": "E-33"
  }
]
"""
}