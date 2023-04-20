package com.example.prackt_16

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class AddTaskActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

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

        val add_task_button = findViewById<Button>(R.id.add_task_button);

        add_task_button.setOnClickListener {
            val task_title_input = findViewById<EditText>(R.id.task_title_input).text
            val task_description_input = findViewById<EditText>(R.id.task_description_input).text

            if (task_title_input.isNotEmpty() && task_description_input.isNotEmpty() && textView.text.isNotEmpty() && dateView.text.isNotEmpty())
            {
                try {
                    val task = Task(
                        task_title_input.toString(),
                        textView.text.toString(),
                        dateView.text.toString(),
                        task_description_input.toString()
                    );
                    val json = addTask(task)
                    saveJson("tasks", json)

                    Toast.makeText(this,R.string.add_task_success, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, CreateAlarmActivity::class.java)
                    startActivity(intent)
                    finish()

                } catch (err: JSONException) {
                    println(err)
                    Toast.makeText(this,R.string.add_task_error, Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this,R.string.add_task_fields_exist, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun addTask(task: Task): String {
        val json = JSONObject()
        json.put("title", task.title)
        json.put("time", task.time)
        json.put("date", task.data)
        json.put("description", task.description)

        return json.toString()
    }

    private fun saveJson(fileName: String, jsonString: String) {
        val output: Writer
        val file = createJsonFile(fileName)
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }

    private fun createJsonFile(fileName: String): File {

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//        if (!storageDir!!.exists()) {storageDir.mkdir()}

        return File.createTempFile(
            fileName,
            "json",
            storageDir
        )

    }
}