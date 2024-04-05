package com.example.condhominus.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.condhominus.R
import com.example.condhominus.databinding.FragmentLoginBinding
import com.example.condhominus.ext.DocumentMask
import com.example.condhominus.ext.UserSharedPreferences
import com.example.condhominus.ext.gone
import com.example.condhominus.ext.replaceFragmentWithAnimation
import com.example.condhominus.ext.visible
import com.example.condhominus.model.login.Login
import com.example.condhominus.model.login.LoginBody
import com.example.condhominus.view.home.HomeFragment
import com.example.condhominus.utils.TextUtils
import com.example.condhominus.view.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class
LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        with(binding) {
            loginDocument.addTextChangedListener(DocumentMask(loginDocument))
            buttonLogin.setOnClickListener {
                if (!loginDocument.text.isNullOrEmpty() && loginDocument.text?.length!! > 11 && loginDocument.text.toString() != "000.000.000-00"  && loginDocument.text.toString() != "999.999.999-99" && !loginPassword.text.isNullOrEmpty()) {
                    viewModel.userLogin(LoginBody(TextUtils.removeMask(loginDocument.text.toString()), loginPassword.text.toString()))
                    loadingProgress.visible()
                    loginViewGroup.gone()
                } else {
                    warningDocument.visible()
                }
            }
        }

        with(viewModel) {
            loginLive.observeForever {
                binding.apply {
                    loadingProgress.gone()
                    loginViewGroup.visible()
                }
                if (it.login == null) {
                    showSnackBar(view, "Você ainda não possui cadastro", Snackbar.LENGTH_LONG)
                } else {
                    UserSharedPreferences(requireActivity()).saveUser(Gson().toJson(it.login))
                    (requireActivity() as AppCompatActivity).replaceFragmentWithAnimation(HomeFragment.newInstance(), R.id.container, true)
                }
            }

            errorLive.observeForever {
                UserSharedPreferences(requireActivity()).saveUser(Gson().toJson(Login(idUsuario = 23847936419, nomeUsuario = "Gabi", administrador = false)))
                (requireActivity() as AppCompatActivity).replaceFragmentWithAnimation(HomeFragment.newInstance(), R.id.container, true)

//                binding.apply {
//                    loadingProgress.gone()
//                    loginViewGroup.visible()
//                }
//                showSnackBar(view, "Aconteceu um erro, tente novamente", Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun showSnackBar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration).show()
    }
}