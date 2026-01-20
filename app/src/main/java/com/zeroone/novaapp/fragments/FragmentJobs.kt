package com.zeroone.novaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterActiveBookings
import com.zeroone.novaapp.adapters.AdapterInvoices
import com.zeroone.novaapp.adapters.AdapterMyAssetCategories
import com.zeroone.novaapp.adapters.AdapterPastJobs
import com.zeroone.novaapp.databinding.FragmentJobsBinding
import com.zeroone.novaapp.home.ActivityActiveJob
import com.zeroone.novaapp.home.ActivityOrderDetails
import com.zeroone.novaapp.home.ActivityPendingBill
import com.zeroone.novaapp.home.InvoicesModel
import com.zeroone.novaapp.responseModels.ActiveBookingsModel
import com.zeroone.novaapp.responseModels.PastJobsModel
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.AppUtils
import com.zeroone.novaapp.viewModels.BookingsViewModel
import com.zeroone.novaapp.viewModels.ServicesViewModel
import com.zeroone.novaapp.viewModels.UtilitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FragmentJobs: Fragment() {

    lateinit var binding: FragmentJobsBinding
    lateinit var adapterMyAssetCategories: AdapterMyAssetCategories
    lateinit var utilitiesViewModel: UtilitiesViewModel
    lateinit var servicesViewModel: ServicesViewModel
    lateinit var adapterPastJobs: AdapterPastJobs
    lateinit var adapterInvoices: AdapterInvoices
    lateinit var adapterActiveBookings: AdapterActiveBookings
    lateinit var bookingsViewModel: BookingsViewModel
    private var selectedServiceDateMs: Long? = null

    //EEE, MMM d, yyyy"
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJobsBinding.inflate(inflater, container, false)

        //init view model
        utilitiesViewModel = ViewModelProvider(this)[UtilitiesViewModel::class.java]

        //service view model
        servicesViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

        //booking view model
        bookingsViewModel = ViewModelProvider(this)[BookingsViewModel::class.java]


        //init categories adapter
        initCategoriesAdapter()

        //init
        init()

        //set listeners
        setListeners()

        //set current date
        setCurrentDateTime()

        return binding.root
    }

    fun init(){
        utilitiesViewModel.assetsCategoriesLiveData?.observe(viewLifecycleOwner) { categories ->
            adapterMyAssetCategories.submitList(categories)
        }
        utilitiesViewModel.processCategories("history")

        servicesViewModel.pastJobsLiveData?.observe(viewLifecycleOwner) {
                jobs->

            showPastJobs(jobs)

        }

        servicesViewModel.invoicesLiveData?.observe(viewLifecycleOwner) {
                invoices->

            showInvoices(invoices)

        }

        //bookings view model
        bookingsViewModel.activeBookingLiveData?.observe(requireActivity(), Observer {
                bookings->

            showActiveJobs(bookings)


        })

    }

    fun setListeners(){
        binding.cardDateFrom.setOnClickListener {
            showServiceDatePicker(binding.txtDateFrom)
        }

        binding.cardDateTo.setOnClickListener {
            showServiceDatePicker(binding.txtDateTo)
        }

    }

    private fun showServiceDatePicker(view: TextView) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(selectedServiceDateMs ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selection?.let {
                selectedServiceDateMs = it
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it
                view.text = dateFormatter.format(calendar.time)
            }
        }

        datePicker.show(parentFragmentManager, "SERVICE_DATE_PICKER")
    }


    fun initCategoriesAdapter(){
        adapterMyAssetCategories = AdapterMyAssetCategories(
            onCategoryClicked = { category ->
                // Handle category click here

                AppLog.Log("clicked_category", category.categoryName)

                when(category.categoryID){
                    "1"->{

                        binding.textLabel.text = getString(R.string.current_jobs)
                        binding.recyclerViewCurrentJobs.visibility = View.VISIBLE
                        binding.recyclerViewPastJobs.visibility = View.GONE
                        binding.recyclerViewInvoices.visibility = View.GONE



                        //trigger past jobs process
                        bookingsViewModel.processActiveBookings()

                    }
                    "2"->{

                        binding.textLabel.text = getString(R.string.past_jobs)
                        binding.recyclerViewPastJobs.visibility = View.VISIBLE
                        binding.recyclerViewInvoices.visibility = View.GONE
                        binding.recyclerViewCurrentJobs.visibility = View.GONE

                        //trigger past jobs process
                        servicesViewModel.processPastJobs()

                    }
                    "3"->{

                        binding.textLabel.text = getString(R.string.invoices)
                        binding.recyclerViewInvoices.visibility = View.VISIBLE
                        binding.recyclerViewPastJobs.visibility = View.GONE
                        binding.recyclerViewCurrentJobs.visibility = View.GONE

                        //trigger invoices process
                        servicesViewModel.processInvoices()

                    }
                }

            })

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHistory.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterMyAssetCategories

        }

    }

    fun initPastJobs(){
        if (!::adapterPastJobs.isInitialized){
            adapterPastJobs = AdapterPastJobs(
                onItemClick = { job ->
                    //action after clicking on item
                    val intent = Intent(context, ActivityOrderDetails::class.java)
                    intent.putExtra("job", job)
                    startActivity(intent)

                    AppUtils.ToastMessage("past job clicked ${job.serviceType}", requireActivity())

                }
            )
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewPastJobs.apply {
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = adapterPastJobs

            }

        }

    }

    fun initInvoicesAdapter(){
        if (!::adapterInvoices.isInitialized){
            adapterInvoices = AdapterInvoices(
                onItemClick = { invoice ->
                    //action after clicking on item
                    val intent = Intent(context, ActivityPendingBill::class.java)
                    intent.putExtra("invoice", invoice)
                    startActivity(intent)

                    AppUtils.ToastMessage("invoice clicked ${invoice.invoiceNumber}", requireActivity())


                }
            )
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewInvoices.apply {
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = adapterInvoices

            }


        }

    }

    fun initActiveBookingsAdapter(){
        if (!::adapterActiveBookings.isInitialized){
            adapterActiveBookings = AdapterActiveBookings(
                onItemClick = {
                        activeBookings->

                    //action after clicking on item
                    val intent = Intent(context, ActivityActiveJob::class.java)
                    intent.putExtra("job", activeBookings)
                    startActivity(intent)

                }
            )

            val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewCurrentJobs.apply {
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = adapterActiveBookings

            }
        }


    }

    fun showActiveJobs(lstPstJobs: MutableList<ActiveBookingsModel>){
        //init past jobs adapter
        initActiveBookingsAdapter()

        //submit list
        adapterActiveBookings.submitList(lstPstJobs)

        // Only update label & visibility if not already showing properties
        if (binding.recyclerViewCurrentJobs.visibility != View.VISIBLE) {
            binding.textLabel.text = getString(R.string.past_jobs)
            binding.recyclerViewCurrentJobs.visibility = View.VISIBLE
            binding.recyclerViewPastJobs.visibility = View.GONE
        }



    }

    fun showPastJobs(lstPstJobs: MutableList<PastJobsModel>){
        //init past jobs adapter
        initPastJobs()

        //submit list
        adapterPastJobs.submitList(lstPstJobs)

        // Only update label & visibility if not already showing properties
        if (binding.recyclerViewPastJobs.visibility != View.VISIBLE) {
            binding.textLabel.text = getString(R.string.past_jobs)
            binding.recyclerViewPastJobs.visibility = View.VISIBLE
            binding.recyclerViewInvoices.visibility = View.GONE
        }

    }

    fun showInvoices(lstInvoices: MutableList<InvoicesModel>){
        //init past jobs adapter
        initInvoicesAdapter()

        //submit list
        adapterInvoices.submitList(lstInvoices)

        // Only update label & visibility if not already showing properties
        if (binding.recyclerViewInvoices.visibility != View.VISIBLE) {
            binding.textLabel.text = getString(R.string.invoices)
            binding.recyclerViewInvoices.visibility = View.VISIBLE
            binding.recyclerViewPastJobs.visibility = View.GONE
        }

    }

    fun setCurrentDateTime(){
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateOnly = dateFormat.format(currentDate)

        binding.txtDateFrom.text = dateOnly
        binding.txtDateTo.text = dateOnly
    }

}