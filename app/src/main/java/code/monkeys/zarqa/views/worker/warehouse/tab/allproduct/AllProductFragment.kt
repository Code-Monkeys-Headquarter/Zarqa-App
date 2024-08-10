package code.monkeys.zarqa.views.worker.warehouse.tab.allproduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import code.monkeys.zarqa.databinding.FragmentAllProductBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.utils.ViewModelFactory
import code.monkeys.zarqa.views.worker.warehouse.tab.adapter.ListProductAdapter
import kotlinx.coroutines.launch

class AllProductFragment : Fragment() {

    private lateinit var binding: FragmentAllProductBinding
    private lateinit var viewModel: AllProductViewModel
    private lateinit var adapter: ListProductAdapter
    private lateinit var dataStoreManager: DataStoreManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllProductBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStoreManager = DataStoreManager.getInstance(requireContext())

        adapter = ListProductAdapter(emptyList())
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = LinearLayoutManager(context)

        val repository = Repository(requireActivity().application)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[AllProductViewModel::class.java]

        viewModel.products.observe(viewLifecycleOwner) { product ->
            adapter.updateData(product)
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            Log.e("AllProductFragment", "Status: $status")
        }

        viewModel.code.observe(viewLifecycleOwner) { code ->
            Log.e("AllProductFragment", "Code: $code")
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("AllProductFragment", "Error Message: $errorMessage")
        }

        lifecycleScope.launch {
            dataStoreManager.tokenFlow.collect { token ->
                if (token != null) {
                    viewModel.fetchProducts(token)
                } else {
                    Log.e("AllProductFragment", "Token is null")
                }
            }
        }

        val token = CommonUtils.showToken(requireContext())
        viewModel.fetchProducts(token)
        Log.e("AllProductFragment", CommonUtils.showToken(requireContext()))


    }

}