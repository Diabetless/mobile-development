package com.ch2Ps073.diabetless.ui.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
<<<<<<< HEAD
import androidx.activity.viewModels
=======
>>>>>>> chello
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.ApiConfig
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import com.ch2Ps073.diabetless.databinding.ActivitySignupBinding
import com.ch2Ps073.diabetless.ui.customView.CustomSignupButton
import com.ch2Ps073.diabetless.ui.customView.PasswordEditText
import com.ch2Ps073.diabetless.ui.login.LoginActivity
<<<<<<< HEAD
import com.ch2Ps073.diabetless.ui.login.LoginViewModel
import com.ch2Ps073.diabetless.ui.login.LoginViewModelFactory
=======
>>>>>>> chello
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel> {
        SignupViewModelFactory.getInstance(this,lifecycleScope)
    }

    private lateinit var binding: ActivitySignupBinding
    private lateinit var myButton: CustomSignupButton
    private lateinit var myEditText: PasswordEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = findViewById(R.id.signupButton)
        myEditText = findViewById(R.id.passwordEditText)

        setMyButtonEnable()

        setupView()
        setupAction()

        myEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

//        binding.signupButton.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

        binding.loginTextButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

<<<<<<< HEAD
            viewModel.signupUser(name, email, username, password)
=======
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.register(name, email, username, password)
                    val toastSuccess = successResponse.message
                    AlertDialog.Builder(this@SignupActivity).apply {
                        setTitle("Email dan password")
                        setMessage("email anda :\"$email\" \npassword anda : \"$password\"\n$toastSuccess")
                        setPositiveButton("Lanjut") { _, _ ->
                            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        }
                        create()
                        show()
                    }
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                    showToast(errorResponse.message)
                    showLoading(false)
                }
            }
>>>>>>> chello
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setMyButtonEnable() {
        val result = myEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}