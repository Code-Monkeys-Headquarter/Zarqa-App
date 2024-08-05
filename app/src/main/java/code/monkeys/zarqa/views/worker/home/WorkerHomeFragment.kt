package code.monkeys.zarqa.views.worker.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.data.source.local.database.AppDatabase
import code.monkeys.zarqa.databinding.FragmentWorkerHomeBinding
import code.monkeys.zarqa.utils.CommonUtils
import kotlinx.coroutines.launch

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launch {
//            getTotalItems()
//            getTotalValue()
//            getItemsOutToday()
//            getItemsAddedToday()
        }


    }

//    private fun getTotalItems() {
//        workerHomeViewModel.getTotalItems().observe(viewLifecycleOwner) {
//            Log.i("WORKER_HOME_INFORMATION", it.toString())
//            binding.tvTotalStockItem.text = it.toString()
//        }
//
//    }
//
//    private fun getTotalValue() {
//
//        workerHomeViewModel.getTotalValue().observe(viewLifecycleOwner) {
//            Log.i("WORKER_HOME_INFORMATION", it.toString())
//            val formattedValue = CommonUtils.formatCurrency(it)
//            binding.tvTotalStockValue.text = formattedValue
//        }
//
//    }
//
//    private fun getItemsAddedToday() {
//        workerHomeViewModel.getItemsAddedToday(CommonUtils.getCurrentDate())
//            .observe(viewLifecycleOwner) {
//                Log.i("WORKER_HOME_INFORMATION", it.toString())
//                binding.tvTotalStockItemInADay.text = it.toString()
//            }
//    }
//
//    private fun getItemsOutToday() {
//
//        workerHomeViewModel.getItemsOutToday(CommonUtils.getCurrentDate())
//
//            .observe(viewLifecycleOwner) {
//                Log.i("WORKER_HOME_INFORMATION", it.toString())
//                binding.tvTotalStockItemOutADay.text = it.toString()
//            }
//
//    }
//
//    private fun resetDatabase() {
//        val application = requireActivity().application
//        AppDatabase.resetDatabase(application)
//        // After resetting the database, you might want to recreate the database and repopulate it if necessary
//        val productDao = AppDatabase.getDatabase(application).productDao()
//        val repository = ProductRepository(productDao)
//        val viewModelFactory = ViewModelFactoryProduct(repository)
//        workerHomeViewModel = ViewModelProvider(this, viewModelFactory)[WorkerHomeViewModel::class.java]
//        // Re-fetch data or handle UI updates here
//    }


}