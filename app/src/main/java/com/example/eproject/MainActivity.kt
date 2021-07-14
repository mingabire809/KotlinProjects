package com.example.eproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    lateinit var binding: com.example.eproject.MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var a = "mike"
        var b = "password"
        var username = findViewById(R.id.username) as EditText
        var password = findViewById(R.id.password) as EditText
        show.setOnClickListener {
            if(show.text.toString().equals("Show")){
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show.text = "Hide"
            }else{
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                show.text = "Show"
            }
        }

sign_in.setOnClickListener {
    var name = username.text.toString()
    var pass = password.text.toString()
if (name ==a && pass ==b ){
    val intent = Intent(this,Home::class.java)
    startActivity(intent)
}else{
    val authentification_error: Toast= Toast.makeText(this,"Wrong username or password!!",Toast.LENGTH_LONG)
    authentification_error.show()
}

}
        sign_up.setOnClickListener {
            val signup = Intent(this,Sign_up::class.java)
            startActivity(signup)
        }
    }
}


