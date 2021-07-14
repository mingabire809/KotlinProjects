package com.example.eproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_up : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val password = findViewById<EditText>(R.id.registering_password) as EditText
        password.addTextChangedListener(this)

        save.setOnClickListener {
            if (registering_password.text.toString() == confirm_password.text.toString()){
            val home = Intent(this, Home::class.java)
            startActivity(home)
            }else{
               val mistake:Toast = Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG)
               mistake.show()
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