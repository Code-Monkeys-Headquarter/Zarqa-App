package code.monkeys.zarqa.views.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityAdminBinding
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.views.auth.login.LoginActivity
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAdminBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStoreManager = DataStoreManager.getInstance(this)

        binding.apply {
            logout()
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            dataStoreManager.clearToken()
            dataStoreManager.clearRole()
            dataStoreManager.clearEmail()
            dataStoreManager.clearPassword()
            startActivity(Intent(this@AdminActivity, LoginActivity::class.java))
            finish()
        }
    }
}