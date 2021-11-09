package com.example.eproject

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class Sign_up : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var firstName = findViewById(R.id.first_name) as EditText
        var lastName = findViewById(R.id.last_name) as EditText
        var email = findViewById(R.id.email) as EditText
        var phoneNumber = findViewById(R.id.phone_number) as EditText
        var password = findViewById<EditText>(R.id.registering_password) as EditText
        password.addTextChangedListener(this)

        save.setOnClickListener {
            if(firstName.text.isNullOrBlank() ||
                lastName.text.isNullOrBlank() ||
                email.text.isNullOrBlank() ||
                password.text.isNullOrBlank() ||
                confirm_password.text.isNullOrBlank() ||
                phoneNumber.text.isNullOrBlank()){
                val requirement = Toast.makeText(
                    this,
                    "Please fill all the required fields",
                    Toast.LENGTH_LONG
                )
                requirement.show()
            }
            else if (registering_password.text.toString() != confirm_password.text.toString()){
                val mistake:Toast = Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG)
                mistake.show()
            }else{
                httpRequest(
                    firstName.text.toString().trim(),
                    lastName.text.toString().trim(),
                    phoneNumber.text.toString().trim(),
                    email.text.toString().trim(),
                    password.text.toString().trim()
                )
            }
        }

    //    var firstName = findViewById(R.id.first_name) as EditText
   //     var lastName = findViewById(R.id.last_name) as EditText
  //      var eMail = findViewById(R.id.email) as EditText
 //       var phoneNumber = findViewById(R.id.phone_number) as EditText
//        var password = findViewById(R.id.password) as EditText
//        var confirmPassword = findViewById(R.id.confirm_password) as EditText
        cancel.setOnClickListener {
           /* firstName.setText("")
            lastName.setText("")
            phoneNumber.setText("")
            eMail.setText("")
            password.setText("")
            confirmPassword.setText("")*/
        }
        displaying.setOnClickListener {
            if (displaying.text.toString().equals("Show")){
                registering_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                confirm_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                displaying.text = "Hide"
            }else{
                registering_password.transformationMethod = PasswordTransformationMethod.getInstance()
                confirm_password.transformationMethod = PasswordTransformationMethod.getInstance()
                displaying.text = "Show"
            }
        }

    }
    private fun httpRequest(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        password: String
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.25.215/login/register.php"

        val progress = ProgressDialog(this)
        progress.setMessage("Please Wait...")
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progress.show()

        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    // response
                    progress.hide()

                    val strResp = response.toString()
                    Log.d("API_M", strResp)

                    val obj = JSONObject(response)
                    val success = obj.getString("success")

                    if (success == "1") {
                        Toast.makeText(this, "Welcome to Eproject", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Home::class.java))
                        overridePendingTransition(0, 0)

                    } else if (success == "2") {
                        Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "try again later", Toast.LENGTH_SHORT).show()
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
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = email
                    params["firstName"] = firstName
                    params["lastName"] = lastName
                    params["phoneNumber"] = phoneNumber
                    params["password"] = password
                    return params
                }
            }
        queue.add(stringReq)
    }
    override fun afterTextChanged(s: Editable) {}
    override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        updatePasswordStrengthView(s.toString())
    }
    private fun updatePasswordStrengthView(password: String) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        val strengthChecker = findViewById<ProgressBar>(R.id.password_strength) as TextView
        if (TextView.VISIBLE != strengthChecker.visibility)
            return
        if (TextUtils.isEmpty(password)) {
            strengthChecker.text = ""
            progressBar.progress = 0
            return
        }
        val str = PasswordStrength.calculateStrength(password)
        strengthChecker.text = str.getText(this)
        strengthChecker.setTextColor(str.color)
        progressBar.progressDrawable.setColorFilter(str.color, android.graphics.PorterDuff.Mode.SRC_IN)
        if (str.getText(this) == "Weak") {
            progressBar.progress = 25
        } else if (str.getText(this) == "Medium") {
            progressBar.progress = 50
        } else if (str.getText(this) == "Strong") {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }
}



enum class PasswordStrength private constructor(internal var resId: Int, color: Int) {

    WEAK(R.string.password_strength_weak, Color.RED),
    MEDIUM(R.string.password_strength_medium, Color.argb(255, 220, 185, 0)),
    STRONG(R.string.password_strength_strong, Color.GREEN),
    VERY_STRONG(R.string.password_strength_very_strong, Color.BLUE);

    var color: Int = 0
        internal set

    init {
        this.color = color
    }

    fun getText(ctx: android.content.Context): CharSequence {
        return ctx.getText(resId)
    }

    companion object {
        internal var REQUIRED_LENGTH = 8
        internal var MAXIMUM_LENGTH = 15
        internal var REQUIRE_SPECIAL_CHARACTERS = true
        internal var REQUIRE_DIGITS = true
        internal var REQUIRE_LOWER_CASE = true
        internal var REQUIRE_UPPER_CASE = false

        fun calculateStrength(password: String): PasswordStrength {
            var currentScore = 0
            var sawUpper = false
            var sawLower = false
            var sawDigit = false
            var sawSpecial = false


            for (i in 0 until password.length) {
                val c = password[i]

                if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                    currentScore += 1
                    sawSpecial = true
                } else {
                    if (!sawDigit && Character.isDigit(c)) {
                        currentScore += 1
                        sawDigit = true
                    } else {
                        if (!sawUpper || !sawLower) {
                            if (Character.isUpperCase(c))
                                sawUpper = true
                            else
                                sawLower = true
                            if (sawUpper && sawLower)
                                currentScore += 1
                        }
                    }
                }

            }

            if (password.length > REQUIRED_LENGTH) {
                if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial
                        || REQUIRE_UPPER_CASE && !sawUpper
                        || REQUIRE_LOWER_CASE && !sawLower
                        || REQUIRE_DIGITS && !sawDigit) {
                    currentScore = 1
                } else {
                    currentScore = 2
                    if (password.length > MAXIMUM_LENGTH) {
                        currentScore = 3
                    }
                }
            } else {
                currentScore = 0
            }

            when (currentScore) {
                0 -> return WEAK
                1 -> return MEDIUM
                2 -> return STRONG
                3 -> return VERY_STRONG
            }

            return VERY_STRONG
        }
    }
}