package code.monkeys.zarqa.views.worker.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.databinding.FragmentWorkerHomeBinding
import code.monkeys.zarqa.repository.Repository
import code.monkeys.zarqa.utils.CommonUtils
import code.monkeys.zarqa.utils.ViewModelFactory

class WorkerHomeFragment : Fragment() {

    private var _binding: FragmentWorkerHomeBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentWorkerHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        fetchData(CommonUtils.showToken(requireContext()))

        swipeRefreshLayout()
        statusObserver()
    }

    private fun swipeRefreshLayout() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() {
        workerHomeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(Application()))
        )[WorkerHomeViewModel::class.java]
    }

    private fun fetchData(token: String) {
        workerHomeViewModel.fetchDashboardSummary(token)

        workerHomeViewModel.dashboardSummary.observe(viewLifecycleOwner) { summary ->
            binding.apply {
                tvTotalProduct.text = summary?.totalProductCount.toString()
                tvTotalSaving.text = "Rp ${summary?.totalProductPrice}"
                tvTotalStock.text = "999"
                tvTotalStockOutADay.text = "999"
                tvTotalProductOutInADay.text = "999"
            }
        }
    }

    private fun statusObserver() {
        workerHomeViewModel.status.observe(viewLifecycleOwner) {
            Log.i("WORKER_DASHBOARD_HOME", "Status : $it")
        }
        workerHomeViewModel.code.observe(viewLifecycleOwner) {
            Log.i("WORKER_DASHBOARD_HOME", "Code : $it")
        }
        workerHomeViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.i("WORKER_DASHBOARD_HOME", "Error Message : $it")
        }
    }


}