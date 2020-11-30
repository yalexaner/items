package yalexaner.items.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.databinding.FragmentAddingItemsBinding

class NewOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAddingItemsBinding.inflate(inflater, container, false)
        val viewModel: OrderViewModel by activityViewModels()

        // enable button when there is a number
        binding.itemsInput.doOnTextChanged { text, _, _, _ ->
            binding.addItems.isEnabled = !text.isNullOrEmpty()
        }

        binding.addItems.setOnClickListener { view: View ->
            viewModel.addItems(binding.itemsInput.text?.toString()?.toInt() ?: 0)
            view.findNavController().navigate(R.id.action_addingItemsFragment_to_itemsFragment)
        }

        return binding.root
    }
}