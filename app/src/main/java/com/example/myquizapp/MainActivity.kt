package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var buttonStart: Button
    lateinit var edit:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart=findViewById(R.id.btn_Start)
        edit=findViewById(R.id.et_name)

        buttonStart.setOnClickListener {
            if(edit.text.isEmpty()){
                  Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG).show()
            }else{
                val intent= Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,edit.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}