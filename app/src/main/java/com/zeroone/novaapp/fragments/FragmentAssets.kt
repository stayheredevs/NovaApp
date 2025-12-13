package com.zeroone.novaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterMyAssetCategories
import com.zeroone.novaapp.adapters.AdapterMyProperties
import com.zeroone.novaapp.adapters.AdapterAllManagers
import com.zeroone.novaapp.databinding.FragmentAssetsBinding
import com.zeroone.novaapp.home.ActivityAddProperty
import com.zeroone.novaapp.home.ActivityManagerDetails
import com.zeroone.novaapp.home.ActivityPropertyDetails
import com.zeroone.novaapp.responseModels.PropertyManagersModel
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import com.zeroone.novaapp.viewModels.UtilitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAssets: Fragment() {

    lateinit var binding: FragmentAssetsBinding
    lateinit var adapterMyAssetCategories: AdapterMyAssetCategories
    lateinit var adapterMyProperties: AdapterMyProperties
    lateinit var adapterMyManagers: AdapterAllManagers
    lateinit var utilitiesViewModel: UtilitiesViewModel
    lateinit var propertiesViewModel: PropertiesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAssetsBinding.inflate(inflater, container, false)

        //init view model
        utilitiesViewModel = ViewModelProvider(this)[UtilitiesViewModel::class.java]

        //init properties view model
        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]


        //init categories adapter
        initCategoriesAdapter()


        //init
        init()

        //event listeners
        setListeners()

        return binding.root
    }

    fun init(){
        utilitiesViewModel.assetsCategoriesLiveData?.observe(viewLifecycleOwner) { categories ->
            adapterMyAssetCategories.submitList(categories)
        }
        utilitiesViewModel.processCategories("assets")

        //properties view model
        propertiesViewModel.propertiesLiveData?.observe(viewLifecycleOwner) {
                properties->

            showProperties(properties)

        }

        //property managers
        propertiesViewModel.propertyManagersLiveData?.observe(viewLifecycleOwner) {
                managers->

            showManagers(managers = managers)

        }

    }

    fun setListeners(){
        //show actions popup menu
        moreActionsMenu()

        //back button
        binding.imgBack.setOnClickListener {
            requireActivity().finish()
        }
    }


    fun initCategoriesAdapter(){
        adapterMyAssetCategories = AdapterMyAssetCategories(
            onCategoryClicked = { category ->
                // Handle category click here

                AppLog.Log("clicked_category", category.categoryName)

                when(category.categoryID){
                    "1"->{

                        binding.textLabel.text = getString(R.string.my_properties)
                        binding.recyclerViewManagers.visibility = View.GONE
                        binding.recyclerViewProperties.visibility = View.VISIBLE
                        //trigger properties process
                        propertiesViewModel.processProperties()

                    }
                    "2"->{

                        binding.textLabel.text = getString(R.string.my_managers)
                        binding.recyclerViewProperties.visibility = View.GONE
                        binding.recyclerViewManagers.visibility = View.VISIBLE

                        //trigger managers process
                        propertiesViewModel.processManagers()


                    }
                }

            })

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAssets.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterMyAssetCategories

        }

    }

    fun initMyPropertiesAdapter(){
        if (!::adapterMyProperties.isInitialized){
            adapterMyProperties = AdapterMyProperties(
                onPropertyClicked = { property ->
                    AppLog.Log("clicked_property", property.propertyName)

                    //navigate to property details
                    val intent = Intent(context, ActivityPropertyDetails::class.java)
                    intent.putExtra("property", property)
                    startActivity(intent)


                })

            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewProperties.apply {
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = adapterMyProperties

            }

        }

    }

    fun initPropertyManagers(){
        if (!::adapterMyManagers.isInitialized){
            adapterMyManagers = AdapterAllManagers(
                onManagerClicked = { manager ->

                    AppLog.Log("clicked_manager", manager.managerName)

                    val intent = Intent(context, ActivityManagerDetails::class.java)
                    intent.putExtra("manager", manager)
                    startActivity(intent)

                }
            )

            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewManagers.apply {
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = adapterMyManagers

            }
        }

    }

    private fun showProperties(properties: List<PropertyModel>) {
        //init properties adapter
        initMyPropertiesAdapter()

        //show first 4 properties
        val limitedList = properties.take(4)

        adapterMyProperties.submitList(limitedList)

        // Only update label & visibility if not already showing properties
        if (binding.recyclerViewProperties.visibility != View.VISIBLE) {
            binding.textLabel.text = getString(R.string.my_properties)
            binding.recyclerViewProperties.visibility = View.VISIBLE
            binding.recyclerViewManagers.visibility = View.GONE
        }

    }

    private fun showManagers(managers: List<PropertyManagersModel>) {
        //init managers adapter
        initPropertyManagers()

        //submit list
        adapterMyManagers.submitList(managers.take(4))

        if (binding.recyclerViewManagers.visibility != View.VISIBLE) {
            binding.textLabel.text = getString(R.string.my_managers)
            binding.recyclerViewManagers.visibility = View.VISIBLE
            binding.recyclerViewProperties.visibility = View.GONE
        }



    }

    fun moreActionsMenu(){
        val popupMenu = PopupMenu(requireContext(), binding.imgMore)
        popupMenu.menuInflater.inflate(R.menu.menu_manage_assets, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_properties -> {

                    val intent = Intent(context, ActivityAddProperty::class.java)
                    startActivity(intent)


                }
                R.id.nav_managers -> {
                    // Handle Add Manager item click
                }
            }
            true
        }

        binding.imgMore.setOnClickListener {
            popupMenu.show()
        }

    }
}