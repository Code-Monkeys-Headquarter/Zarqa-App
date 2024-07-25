package code.monkeys.zarqa.views.worker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.data.source.local.database.AppDatabase
import code.monkeys.zarqa.databinding.FragmentWorkerHomeBinding
import code.monkeys.zarqa.repository.ProductRepository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.ViewModelFactoryProduct
import code.monkeys.zarqa.views.worker.warehouse.product.add.AddProductViewModel

class WorkerHomeFragment : Fragment() {

    private lateinit var binding: FragmentWorkerHomeBinding
    private lateinit var workerHomeViewModel: WorkerHomeViewModel

    companion object {
        fun newInstance(): WorkerHomeFragment {
            val fragment = WorkerHomeFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val productDao = AppDatabase.getDatabase(application).productDao()
        val repository = ProductRepository(productDao)
        val viewModelFactory = ViewModelFactoryProduct(repository)
        workerHomeViewModel = ViewModelProvider(this, viewModelFactory)[WorkerHomeViewModel::class.java]

        val currentDate = CommonUtils.getCurrentDate()

        binding.apply {
            workerHomeViewModel.getTotalItems().observe(viewLifecycleOwner) {
                tvTotalStockItem.text = it.toString()
            }

            workerHomeViewModel.getTotalValue().observe(viewLifecycleOwner) {
                tvTotalStockValue.text = it.toString()
            }

            workerHomeViewModel.getItemsAddedToday(currentDate).observe(viewLifecycleOwner) {
                tvTotalStockItemInADay.text = it.toString()
            }
1
            workerHomeViewModel.getItemsOutToday(currentDate).observe(viewLifecycleOwner) {
                tvTotalStockItemOutADay.text = it.toString()
            }


        }
    }
}