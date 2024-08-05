package code.monkeys.zarqa.views.worker.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import code.monkeys.zarqa.databinding.FragmentWorkerSettingBinding
import code.monkeys.zarqa.utils.DataStoreManager
import code.monkeys.zarqa.views.auth.login.LoginActivity
import kotlinx.coroutines.launch

class WorkerSettingFragment : Fragment() {

    private lateinit var binding: FragmentWorkerSettingBinding
    private lateinit var dataStoreManager: DataStoreManager

    companion object {
        fun newInstance(): WorkerSettingFragment {
            val fragment = WorkerSettingFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: WorkerSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager.getInstance(requireContext())

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkerSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            btnLogout.setOnClickListener {
                logout()
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            dataStoreManager.clearRole()
            dataStoreManager.clearRole()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}