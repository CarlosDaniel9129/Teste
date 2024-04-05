package com.example.condhominus.view.financial

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.condhominus.databinding.FragmentFinancialBinding
import com.example.condhominus.view.financial.viewmodel.FinancialViewModel

class FinancialFragment : Fragment() {

    private var _binding: FragmentFinancialBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FinancialViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinancialBinding.inflate(inflater, container, false)
        requestPermissions()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FinancialFragment()

        private const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1001
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1002
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    // Permissão negada
                }
            }
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    // Permissão negada
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FinancialViewModel::class.java]
        with(binding) {
            firstCardBilletView.setOnClickListener {
                viewModel.getBillet(requireContext())
            }
        }
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
        }
    }

}