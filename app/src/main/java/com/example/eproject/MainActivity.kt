package com.example.eproject

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.eproject.models.API
import com.example.eproject.register.myApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

import com.android.volley.Response
import com.example.eproject.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: com.example.eproject.MainActivity
    internal lateinit var mService: myApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mService = Common.api
        var a = "mike"
        var b = "password"
        var username = findViewById(R.id.username) as EditText
        var password = findViewById(R.id.password) as EditText
        show.setOnClickListener {
            if (show.text.toString().equals("Show")) {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show.text = "Hide"
            } else {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                show.text = "Show"
            }
        }

        sign_in.setOnClickListener {
            var email = username.text.toString().trim()
            var pass = password.text.toString().trim()
            if (validateInputs()) {
                httpRequest(email, pass)
            }
        }

        sign_up.setOnClickListener {
            val signup = Intent(this, Sign_up::class.java)
            startActivity(signup)
        }
    }

    private fun validateInputs(): Boolean {
        validateUsername()
        validatePassword()
        return validateUsername() && validatePassword()
    }

    private fun validateUsername(): Boolean {
        val input: EditText = findViewById(R.id.username)
        if (input.text.toString().isEmpty()) {
            input.error = "username id required"
            return false

        }
        return true
    }

    private fun validatePassword(): Boolean {
        val input: EditText = findViewById(R.id.password)
        if (input.text.toString().isEmpty()) {
            input.error = "password required"
            return false
        }
        return true
    }


    private fun httpRequest(username: String, password: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.25.215/login/login2.php"
//        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
//            prefKey, Context.MODE_PRIVATE
//        )

        val progress = ProgressDialog(this)
        progress.setMessage("Please Wait...")
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progress.show()

        val stringReq: StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    // response
                    progress.hide()

                    val strResp = response.toString()
                    Log.d("API_M", strResp)

                    val obj = JSONObject(response)
                    val success = obj.getString("success")

                    if (success == "1") {

                        startActivity(Intent(this, Home::class.java))
                        /*Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
                            .show()*/
                        overridePendingTransition(0, 0)

                    } else if (success == "0") {
                        Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Account no found", Toast.LENGTH_SHORT).show()
                    }

                },
                Response.ErrorListener { error ->
                    progress.hide()
                    Log.d("API_M", "error => $error")
                    Toast.makeText(
                        this, "Error code: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val map = HashMap<String, String>()
                    map["email"] = username
                    map["password"] = password
                    return map
                }
            }
        queue.add(stringReq)
    }
//    private fun authenticateUser(email: String, password: String) {
//        mService.logging_in(email, password).enqueue(object : Callback<API> {
//            override fun onResponse(call: Call<API>, response: Response<API>) {
//                if (response!!.body()!!.isError)
//                    Toast.makeText(
//                        this@MainActivity,
//                        response!!.body()!!.error_mag,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                else
//                    Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onFailure(call: Call<API>, t: Throwable) {
//                Toast.makeText(this@MainActivity, t!!.message, Toast.LENGTH_SHORT).show()
//                println(t!!.message)
//            }
//
//        })
//
//    }
}


