package ru.dvizh.client.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.musfickjamil.snackify.Snackify
//import com.musfickjamil.snackify.Snackify
import ru.dvizh.client.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private val length = Snackify.LENGTH_SHORT
    fun showSuccess(
        view: View = requireActivity().findViewById(R.id.main_container),
        message: String?
    ) {
        Snackify.success(view, message ?: "Успешно!", length).show()
    }

    fun showInfo(
        view: View = requireActivity().findViewById(R.id.main_container),
        message: String
    ) {
        Snackify.info(view, message, length).show()
    }

    fun showWarning(
        view: View = requireActivity().findViewById(R.id.main_container),
        message: String
    ) {
        Snackify.warning(view, message, length).show()
    }

    fun showError(
        view: View = requireActivity().findViewById(R.id.main_container),
        message: String?
    ) {
        Snackify.error(view, message ?: "Ошибка!", length).show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}