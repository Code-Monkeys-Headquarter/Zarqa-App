package code.monkeys.zarqa.views.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityRegisterBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.auth.login.LoginActivity

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(Repository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val items = listOf("Admin", "Pekerja Gudang", "Dropshipper")
        val adapter = ArrayAdapter(this, R.layout.list_item_role, items)

        binding.apply {
            edtRoleItem.setAdapter(adapter)
            btnRegister.setOnClickListener {
                val edtEmail = edtEmail.text.toString()
                val edtPassword = edtPassword.text.toString()
                val edtRole = edtRoleItem.text.toString()
                val edtFullname = edtFullname.text.toString()

                if (validateInput(edtFullname, edtEmail, edtPassword, edtRole)) {
                    registerViewModel.register(edtFullname, edtEmail, edtPassword, edtRole)
                }
            }
            btnAlreadyHaveAccount.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }
        }


    }

    private fun validateInput(
        email: String,
        password: String,
        role: String,
        fullname: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            CommonUtils.showToast(this@RegisterActivity, "Silahkan isi semua data")
            return false
        }

        if (role.isEmpty()) {
            CommonUtils.showToast(this@RegisterActivity, "Silahkan pilih role")
            return false
        }
        if (password.length < 5) {
            CommonUtils.showToast(this@RegisterActivity, "Password minimal 5 karakter")
            return false
        }

        return true
    }

}