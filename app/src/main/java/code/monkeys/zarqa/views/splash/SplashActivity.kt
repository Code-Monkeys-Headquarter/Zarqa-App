package code.monkeys.zarqa.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivitySplashBinding
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.views.admin.AdminActivity
import code.monkeys.zarqa.views.auth.login.LoginActivity
import code.monkeys.zarqa.views.dropshipper.DropshipperActivity
import code.monkeys.zarqa.views.worker.WorkerActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var dataStoreManager: DataStoreManager

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

        dataStoreManager = DataStoreManager.getInstance(this)

        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000)

    }

    private fun checkLoginStatus() {

        lifecycleScope.launch {
            val token = dataStoreManager.tokenFlow.first()
            val role = dataStoreManager.roleFlow.first()

            if (token != null && role != null) {
                navitageToAppropriatePage(role)
            } else {
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