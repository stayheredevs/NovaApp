package com.zeroone.novaapp.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterActions
import com.zeroone.novaapp.adapters.AdapterAllocatedManagers
import com.zeroone.novaapp.databinding.ActionsBottomSheetBinding
import com.zeroone.novaapp.databinding.ActivityPropertyDetailsBinding
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityPropertyDetails: AppCompatActivity() {

    lateinit var binding: ActivityPropertyDetailsBinding
    lateinit var adapterAllocatedManagers: AdapterAllocatedManagers
    lateinit var propertiesViewModel: PropertiesViewModel

    //Making the adapter a property of the Fragment, not recreated per bottom sheet.
    private val adapterActions by lazy {
        AdapterActions(
            onClickListener = {

                AppLog.Log("clicked_action", it.action)

            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]


        //init managers adapter
        initManagersAdapter()

        //init
        init()

        //set listeners
        setListeners()
    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun init(){
        val propertyDetails = intent.extras?.getParcelable<PropertyModel>("property")

        binding.txtPropertyName.text = propertyDetails?.propertyName.toString()

        propertiesViewModel.propertyManagersLiveData?.observe(this, Observer{
            managerList->

            adapterAllocatedManagers.submitList(managerList.take(3))
        })
        propertiesViewModel.processAllocatedManagers()


        propertiesViewModel.actionsLiveData?.observe(this, Observer {
                actions->

            //submit list
            adapterActions.submitList(actions)
        })

    }

    fun initManagersAdapter(){
        adapterAllocatedManagers = AdapterAllocatedManagers(
            onClickListener = {

                showBottomSheetDialog(this)


            }
        )

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewManagers.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterAllocatedManagers
        }
    }

    private fun showBottomSheetDialog(context: Context?) {
        val sheetBinding = ActionsBottomSheetBinding.inflate(LayoutInflater.from(context), null, false)

        val dialog = BottomSheetDialog(context!!, R.style.bottomSheetDialogTheme)
        dialog.setContentView(sheetBinding.root)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sheetBinding.recyclerViewActions.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterActions

            // Add divider (separator)
            // Add divider only the first time
            if (itemDecorationCount == 0) {
                addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            }
        }

        propertiesViewModel.processActions()

        dialog.show()
    }



}