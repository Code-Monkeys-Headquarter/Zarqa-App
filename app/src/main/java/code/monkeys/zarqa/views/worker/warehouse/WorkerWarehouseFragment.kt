package code.monkeys.zarqa.views.worker.warehouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import code.monkeys.zarqa.R
import code.monkeys.zarqa.databinding.FragmentWorkerWarehouseBinding
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.views.worker.warehouse.product.add.AddProductActivity
import code.monkeys.zarqa.views.worker.warehouse.product.restock.TakeProductActivity
import code.monkeys.zarqa.views.worker.warehouse.tab.allproduct.AllProductActivity

class WorkerWarehouseFragment : Fragment() {

    private lateinit var binding: FragmentWorkerWarehouseBinding

    companion object {
        fun newInstance(): WorkerWarehouseFragment {
            val fragment = WorkerWarehouseFragment()
            fragment.arguments = Bundle()
            return fragment
        }

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.worker_warehouse_tab_layout_title1,
            R.string.worker_warehouse_tab_layout_title2,
            R.string.worker_warehouse_tab_layout_title3
        )
    }

    private val viewModel: WorkerWarehouseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerWarehouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val sectionPagerAdapter = SectionPagerAdapter(this)
//        val viewPager = binding.viewPager
//        viewPager.adapter = sectionPagerAdapter
//        val tabs = binding.tabLayout
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = resources.getString(TAB_TITLES[position])
//        }.attach()

        binding.apply {
            btnAddProduct.setOnClickListener {
                val intent = Intent(requireContext(), AddProductActivity::class.java)
                startActivity(intent)
            }

            btnTakeProduct.setOnClickListener {
                val intent = Intent(requireContext(), TakeProductActivity::class.java)
                startActivity(intent)
                Log.i("TOKEN", "Token : ${CommonUtils.showToken(requireContext())}")
            }

            cvAllProduct.setOnClickListener {
                val intent = Intent(requireContext(), AllProductActivity::class.java)
                startActivity(intent)
            }

        }
    }
}