package code.monkeys.zarqa.views.splash

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivitySplashBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.admin.AdminActivity
import code.monkeys.zarqa.views.auth.login.LoginActivity
import code.monkeys.zarqa.views.auth.login.LoginViewModel
import code.monkeys.zarqa.views.dropshipper.DropshipperActivity
import code.monkeys.zarqa.views.worker.WorkerActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[LoginViewModel::class.java]

        dataStoreManager = DataStoreManager.getInstance(this)

        automatedLogin()

    }

//    Automated Login
private fun automatedLogin() {
    lifecycleScope.launch {
        try {
            val email = dataStoreManager.emailFlow.first()
            val password = dataStoreManager.passwordFlow.first()

            if (email != null && password != null) {
                loginViewModel.login(email, password)
                // Lakukan login otomatis
                loginViewModel.loginResponse.observe(this@SplashActivity) { result ->
                    if (result != null) {
                        val role = result.data.role
                        navitageToAppropriatePage(role)
                    } else {
                        navigateToLoginPage()
                    }
                }
            } else {
                navigateToLoginPage()
            }
        } catch (e: Exception) {
            navigateToLoginPage()
        }
    }
}


    private fun navitageToAppropriatePage(role: String) {
        when (role) {
            "admin" -> navigateToAdminActivity()
            "dropshipper" -> navigateToDropshipperActivity()
            "gudang" -> navigateToWorkerActivity()
            else -> navigateToLoginPage()
        }
        finish()
    }

    private fun navigateToWorkerActivity() {
        startActivity(Intent(this, WorkerActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToDropshipperActivity() {
        startActivity(Intent(this, DropshipperActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToAdminActivity() {
        startActivity(Intent(this, AdminActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}