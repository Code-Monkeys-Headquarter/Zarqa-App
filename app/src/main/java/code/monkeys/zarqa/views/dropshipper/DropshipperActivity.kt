package code.monkeys.zarqa.views.dropshipper

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityDropshipperBinding
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.views.auth.login.LoginActivity
import kotlinx.coroutines.launch

class DropshipperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDropshipperBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDropshipperBinding.inflate(layoutInflater)
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
            btnLogout.setOnClickListener {
                logout()
            }
        }


    }

    private fun logout() {
        lifecycleScope.launch {
            dataStoreManager.clearToken()
            dataStoreManager.clearRole()

            val intent = Intent(this@DropshipperActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}