package code.monkeys.zarqa.views.worker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import code.monkeys.zarqa.databinding.FragmentWorkerHomeBinding

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

        swipeRefreshLayout()
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


}