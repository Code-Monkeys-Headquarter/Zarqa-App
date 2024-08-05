package code.monkeys.zarqa.views.auth.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityLoginBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.admin.AdminActivity
import code.monkeys.zarqa.views.auth.register.RegisterActivity
import code.monkeys.zarqa.views.dropshipper.DropshipperActivity
import code.monkeys.zarqa.views.worker.WorkerActivity
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStoreManager = DataStoreManager.getInstance(this)

        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(Repository(Application()))
        )[LoginViewModel::class.java]

        loginViewModel.loginResponse.observe(this) { response ->
            response?.let {
                hideLoading()
                when (it.data.role) {
                    "admin" -> navigateToAdminActivity()
                    "dropshipper" -> navigateToDropshipperActivity()
                    "gudang" -> navigateToWorkerActivity()
                    else -> CommonUtils.showToast(this, "Role tidak ditemukan😂")
                }
                val token = it.data.token
                val role = it.data.role
                saveToken(token)
                saveRole(role)
            }

        }

        loginViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                hideLoading()
                CommonUtils.showToast(this, it)
            }
        }


        binding.apply {
            btnCreateNewAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                if (validateInput(email, password)) {
                    showLoading()
                    loginViewModel.login(email, password)

                } else {
                    Toast.makeText(this@LoginActivity, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            CommonUtils.showToast(this@LoginActivity, "Silahkan isi semua data")
            return false
        }

        if (password.length < 8) {
            CommonUtils.showToast(this@LoginActivity, "Password minimal 8 karakter")
            return false
        }
        return true
    }

    private fun navigateToWorkerActivity() {
        startActivity(Intent(this@LoginActivity, WorkerActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToDropshipperActivity() {
        startActivity(Intent(this@LoginActivity, DropshipperActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToAdminActivity() {
        startActivity(Intent(this@LoginActivity, AdminActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()

    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
        binding.apply {
            btnLogin.isEnabled = false
            btnLogin.text = "Loading....."
            btnLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, android.R.color.white))
        }
    }

    private fun hideLoading() {
        binding.apply {
            btnLogin.isEnabled = true
            btnLogin.text = getString(R.string.login)
            btnLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, android.R.color.white))
        }
    }

    private fun saveToken(token: String) {
        lifecycleScope.launch {
            dataStoreManager.saveToken(token)
        }
    }

    private fun saveRole(role: String) {
        lifecycleScope.launch {
            dataStoreManager.saveRole(role)
        }
    }

}