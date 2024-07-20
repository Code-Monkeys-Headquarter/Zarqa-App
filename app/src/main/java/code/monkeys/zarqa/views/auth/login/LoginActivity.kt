package code.monkeys.zarqa.views.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityLoginBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.admin.AdminActivity
import code.monkeys.zarqa.views.auth.register.RegisterActivity
import code.monkeys.zarqa.views.dropshipper.DropshipperActivity
import code.monkeys.zarqa.views.worker.WorkerActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(Repository(this))
    }

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

//        List Level Item
        val items = listOf("Admin", "Pekerja Gudang", "Dropshipper")
        val adapter = ArrayAdapter(this, R.layout.list_item_role, items)
        binding.apply {
            edtRoleItem.setAdapter(adapter)
            btnCreateNewAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val role = edtRoleItem.text.toString()
                if (validateInput(email, password, role)) {
                    loginViewModel.login(email, password, role)
                }
            }
        }

//        ViewModel
        loginViewModel.loginResult.observe(this, Observer { result ->
            result.fold(
                onSuccess = { user ->
                    Toast.makeText(
                        this,
                        "Login berhasil sebagai ${user.role}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = when (user.role) {
                        "Admin" -> Intent(this, AdminActivity::class.java)
                        "Pekerja Gudang" -> Intent(this, WorkerActivity::class.java)
                        "Dropshipper" -> Intent(this, DropshipperActivity::class.java)
                        else -> null
                    }
                    intent?.let {
                        startActivity(it)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        finish()
                    }
                },
                onFailure = { error ->
                    Toast.makeText(this, "Login gagal: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        })


    }

    private fun validateInput(email: String, password: String, role: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            CommonUtils.showToast(this@LoginActivity, "Silahkan isi semua data")
            return false
        }
        if (role.isEmpty()) {
            CommonUtils.showToast(this@LoginActivity, "Silahkan pilih role")
            return false
        }
        if (password.length < 5) {
            CommonUtils.showToast(this@LoginActivity, "Password minimal 5 karakter")
            return false
        }
        return true
    }
}