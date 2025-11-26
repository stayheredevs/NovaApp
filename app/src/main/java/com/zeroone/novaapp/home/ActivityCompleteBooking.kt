package com.zeroone.novaapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityCompleteBookingBinding
import com.zeroone.novaapp.responseModels.BookingDetails
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.utilities.AppUtils
import com.zeroone.novaapp.utilities.EdgeToEdgeManager
import com.zeroone.novaapp.utilities.SharedPreference
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class ActivityCompleteBooking: AppCompatActivity() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    lateinit var binding: ActivityCompleteBookingBinding
    private var selectedServiceDateMs: Long? = null
    private val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    
    private var startHour: Int = 9
    private var startMinute: Int = 0
    private var endHour: Int = 11
    private var endMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompleteBookingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)
        
        // Setup keyboard handling to ensure EditText and button remain visible
        setupKeyboardHandling()

        //on back pressed
        handleOnBackPressed()

        //init
        init()

        //set listeners
        setListeners()


    }

    override fun onResume() {
        super.onResume()

        val selectedProperty: MutableList<PropertyModel>? = sharedPreference.selectedProperty

        if (selectedProperty != null) {
            binding.txtPropertyName.text = selectedProperty[0].propertyName

        }

        val selectedService: MutableList<BookingDetails>? = sharedPreference.selectedService
        if (selectedService != null) {
            binding.txtServiceName.text = selectedService[0].serviceName
            binding.txtOptionDetails.text = selectedService[0].serviceOptionName
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        sharedPreference.selectedProperty   = null
        sharedPreference.selectedService    = null
    }

    fun init(){
        val bookingDetails: BookingDetails? = intent.extras?.getParcelable("booking_details")!!

        AppLog.Log("property_name", bookingDetails?.propertyName)
        AppLog.Log("service_name", bookingDetails?.serviceOptionName)
        AppLog.Log("price_name", bookingDetails?.serviceOptionName)


        if (bookingDetails?.propertyName != ""){
            binding.txtPropertyName.text    = bookingDetails?.propertyName
        }

        if (bookingDetails?.propertyAddress != ""){
            binding.txtPropertyAddress.text = bookingDetails?.propertyAddress
        }

        if (bookingDetails?.serviceName != ""){
            binding.txtServiceName.text     = bookingDetails?.serviceName

        }

        if (bookingDetails?.serviceOptionName != ""){
            binding.txtOptionDetails.text   = bookingDetails?.serviceOptionName
        }

    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {

            sharedPreference.selectedProperty = null

            finish()
        }

        binding.cardDatePicker.setOnClickListener {
            showServiceDatePicker()
        }

        binding.cardStartTime.setOnClickListener {
            showStartTimePicker()
        }

        binding.cardEndTime.setOnClickListener {
            showEndTimePicker()
        }

        binding.imgEditService.setOnClickListener {
            val intent = Intent(this, ActivityAllServices::class.java)
            startActivity(intent)

            sharedPreference.eventSource = "BOOKING_COMPLETE"

        }

        binding.imgEditProperty.setOnClickListener {
            val intent = Intent(this, ActivityAllProperties::class.java)
            startActivity(intent)

            sharedPreference.eventSource = "BOOKING_COMPLETE"
        }

        binding.btnSubmitBooking.setOnClickListener {

        }


    }
    private fun showServiceDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(selectedServiceDateMs ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selection?.let {
                selectedServiceDateMs = it
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it
                binding.txtSelectedDate.text = dateFormatter.format(calendar.time)
            }
        }

        datePicker.show(supportFragmentManager, "SERVICE_DATE_PICKER")
    }
    
    private fun showStartTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(startHour)
            .setMinute(startMinute)
            .setTitleText("Select Start Time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()
        
        timePicker.addOnPositiveButtonClickListener {
            startHour = timePicker.hour
            startMinute = timePicker.minute
            updateStartTimeDisplay()
        }
        
        timePicker.show(supportFragmentManager, "START_TIME_PICKER")
    }
    
    private fun showEndTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(endHour)
            .setMinute(endMinute)
            .setTitleText("Select End Time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()
        
        timePicker.addOnPositiveButtonClickListener {
            endHour = timePicker.hour
            endMinute = timePicker.minute
            updateEndTimeDisplay()
            
            // Validate that end time is after start time
            validateTimeRange()
        }
        
        timePicker.show(supportFragmentManager, "END_TIME_PICKER")
    }
    
    private fun updateStartTimeDisplay() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        binding.txtStartTime.text = timeFormatter.format(calendar.time)
    }
    
    private fun updateEndTimeDisplay() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, endHour)
        calendar.set(Calendar.MINUTE, endMinute)
        binding.txtEndTime.text = timeFormatter.format(calendar.time)
    }
    
    private fun validateTimeRange() {
        val startTimeMinutes = startHour * 60 + startMinute
        val endTimeMinutes = endHour * 60 + endMinute
        
        if (endTimeMinutes <= startTimeMinutes) {
            // Show error message
            AppUtils.ToastMessage("End time must be after start time", this)
            
            // Auto-adjust end time to be 2 hours after start time
            val adjustedEndMinutes = startTimeMinutes + 120 // 2 hours
            endHour = (adjustedEndMinutes / 60) % 24
            endMinute = adjustedEndMinutes % 60
            updateEndTimeDisplay()
        }
    }

    fun handleOnBackPressed(){
        onBackPressedDispatcher.addCallback(this){
            sharedPreference.selectedProperty = null
            sharedPreference.selectedService    = null
            finish()
        }
    }

    private fun setupKeyboardHandling() {
        // Handle IME (keyboard) insets separately to allow adjustResize to work
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            // Add bottom padding to ScrollView when keyboard appears
            view.setPadding(0, 0, 0, imeInsets.bottom)
            // Don't consume IME insets so adjustResize can work
            insets
        }
        
        // Scroll to EditText when it gains focus
        binding.editTextNotes.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.postDelayed({
                    binding.scrollView.post {
                        val scrollBounds = android.graphics.Rect()
                        binding.scrollView.getHitRect(scrollBounds)
                        if (!view.getLocalVisibleRect(scrollBounds)) {
                            val scrollTo = view.top - binding.scrollView.height / 3
                            binding.scrollView.smoothScrollTo(0, scrollTo.coerceAtLeast(0))
                        }
                    }
                }, 200)
            }
        }
    }
}