package code.monkeys.zarqa.views.worker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.ActivityWorkerBinding
import code.monkeys.zarqa.views.main.home.HomeFragment
import code.monkeys.zarqa.views.worker.history.WorkerHistoryFragment
import code.monkeys.zarqa.views.worker.home.WorkerHomeFragment
import code.monkeys.zarqa.views.worker.setting.WorkerSettingFragment
import code.monkeys.zarqa.views.worker.warehouse.WorkerWarehouseFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class WorkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWorkerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fragmentTransaction()
    }

    private fun fragmentManager(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }

    private fun fragmentTransaction() {
        binding.apply {
            bottomNav.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
                override fun onItemSelected(id: Int) {
                    when (id) {
                        R.id.nav_home -> fragmentManager(WorkerHomeFragment())
                        R.id.nav_warehouse -> fragmentManager(WorkerWarehouseFragment())
                        R.id.nav_history -> fragmentManager(WorkerHistoryFragment())
                        R.id.nav_setting -> fragmentManager(WorkerSettingFragment())
                    }
                }

            })
            fragmentManager(WorkerHomeFragment.newInstance())
            bottomNav.setItemSelected(R.id.nav_home)
        }
    }
}