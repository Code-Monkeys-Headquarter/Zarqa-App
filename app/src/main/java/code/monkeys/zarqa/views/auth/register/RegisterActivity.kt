package code.monkeys.zarqa.views.auth.register

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityRegisterBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.auth.login.LoginActivity
import code.monkeys.zarqa.views.dropshipper.DropshipperActivity

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

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
        val dropshipperRole = "dropshipper"
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[RegisterViewModel::class.java]

        registerViewModel.registerResponse.observe(this) {
            hideLoading()
            it?.let {
                hideLoading()
                if (it.status == "success") {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }

        registerViewModel.error.observe(this) {
            hideLoading()
            it?.let {
                CommonUtils.showToast(this, it)
            }
        }

        binding.apply {
            btnRegister.setOnClickListener {
                val edtEmail = edtEmail.text.toString()
                val edtPassword = edtPassword.text.toString()
                val edtShopName = edtShopName.text.toString()
                val edtFullname = edtFullname.text.toString()
                val edtPhone = edtPhone.text.toString()
                if (validateInput(edtEmail, edtPassword, edtShopName, edtFullname, edtPhone)) {
                    showLoading()
                    registerViewModel.register(
                        edtFullname,
                        edtShopName,
                        edtPhone,
                        edtEmail,
                        edtPassword,
                        dropshipperRole
                    )
                } else {
                    CommonUtils.showToast(this@RegisterActivity, "Register Gagal")
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
        shopName: String,
        fullname: String,
        phone: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || fullname.isEmpty() || shopName.isEmpty() || phone.isEmpty()) {
            CommonUtils.showToast(this@RegisterActivity, "Silahkan isi semua data")
            return false
        }
        if (password.length < 8) {
            CommonUtils.showToast(this@RegisterActivity, "Password minimal 8 karakter")
            return false
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
        binding.apply {
            btnRegister.isEnabled = false
            btnRegister.text = "Loading....."
            btnRegister.setTextColor(
                ContextCompat.getColor(
                    this@RegisterActivity,
                    android.R.color.white
                )
            )
        }
    }

    private fun hideLoading() {
        binding.apply {
            btnRegister.isEnabled = true
            btnRegister.text = getString(R.string.register)
            btnRegister.setTextColor(
                ContextCompat.getColor(
                    this@RegisterActivity,
                    android.R.color.white
                )
            )
        }
    }


}