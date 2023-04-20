package com.example.prackt_16

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

class CreateAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        val textView = findViewById<TextView>(R.id.timeTv)

        textView.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }

        val dateView = findViewById<TextView>(R.id.dateTv)

        dateView.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                cal.set(Calendar.DAY_OF_MONTH, i)
                cal.set(Calendar.MONTH, i2)
                cal.set(Calendar.YEAR, i3)
                dateView.text = "${i3+1}-${i2+1}-${i}"
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)).show()
        }

        val create_alarm_btn = findViewById<Button>(R.id.create_alarm)

        create_alarm_btn.setOnClickListener {
            val time = findViewById<TextView>(R.id.timeTv).text
            val date = findViewById<TextView>(R.id.dateTv).text
//            val checks = findViewById<CheckBox>()

            if (time.isNotEmpty() && date.isNotEmpty()) {
                Toast.makeText(this,R.string.add_task_success, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, EditAlarmActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this,R.string.add_task_fields_exist, Toast.LENGTH_SHORT).show()
            }
        }
    }
}