package com.hawk.ageninminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)


        btnDatePicker.setOnClickListener{
           clickDatePicker()
        }
    }

    private fun clickDatePicker() {

        // calendar object to get year, month and day
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        // store date picker
        val dpd =  DatePickerDialog(this,
            // DatePickerDialog.OnDateSetListener
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                // once date is selected, execute this code
                val selectedDate = "${selectedDayOfMonth}/${selectedMonth+1}/${selectedYear}"
                tvSelectedDate?.text = selectedDate

                // date format
                val sdf =  SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                // null safe
                // only if date is not empty run this
                theDate?.let {
                    // getTime() or .time returns the number of milliseconds since 1970
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            },
            year, month, day
        )
        //assign max date (86400000 mls in an day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        // show date picker
        dpd.show()
    }
}