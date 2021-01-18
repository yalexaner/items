package yalexaner.items.ui.neworder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import yalexaner.items.R
import yalexaner.items.databinding.ActivityNewOrderBinding

class NewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNewOrderBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_new_order)

        binding.itemsCollected.apply {
            showSoftKeyboard()
            doOnTextChanged { text, _, _, _ ->
                // enable button when there is a number
                binding.addItems.isEnabled = !text.isNullOrEmpty() && !text.startsWith('0')
                binding.itemsOrdered.setText(text)
            }
        }

        binding.addItems.setOnClickListener {
            val itemsCollected: Int = binding.itemsCollected.text.toString().toInt()
            val itemsOrdered: Int =
                binding.itemsOrdered.text?.toString().takeIf { !it.isNullOrEmpty() }?.toInt() ?: 0

            if (itemsOrdered == 0 || itemsOrdered < itemsCollected) {
                Snackbar.make(
                    binding.root,
                    if (itemsOrdered == 0) R.string.cant_be_zero_or_empty else R.string.cant_be_less,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val replyIntent = Intent()

            replyIntent.apply {
                putExtra(ITEMS_COLLECTED, itemsCollected)
                putExtra(ITEMS_ORDERED, itemsOrdered)
            }
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    private fun View.showSoftKeyboard() {
        if (this.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    companion object {
        const val ITEMS_COLLECTED = "yalexaner.items.ITEMS_COLLECTED"
        const val ITEMS_ORDERED = "yalexaner.items.ITEMS_ORDERED"
    }
}