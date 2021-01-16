package yalexaner.items.ui.neworder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import yalexaner.items.R
import yalexaner.items.databinding.ActivityNewOrderBinding

class NewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNewOrderBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_new_order)
        val itemsCollected = binding.itemsCollected
        val itemsOrdered = binding.itemsOrdered
        val addItems = binding.addItems

        // enable button when there is a number
        itemsCollected.doOnTextChanged { text, _, _, _ ->
            addItems.isEnabled = !text.isNullOrEmpty()

            if (addItems.isEnabled) {
                itemsOrdered.hint = text
            }
        }

        @Suppress("NAME_SHADOWING")
        addItems.setOnClickListener {
            val itemsCollected = itemsCollected.text.toString().toInt()
            val itemsOrdered = itemsOrdered.text.toString().toInt()
            val replyIntent = Intent()

            with(replyIntent) {
                putExtra(ITEMS_COLLECTED, itemsCollected)
                putExtra(ITEMS_ORDERED, itemsOrdered)
            }
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    companion object {
        const val ITEMS_COLLECTED = "yalexaner.items.ITEMS_COLLECTED"
        const val ITEMS_ORDERED = "yalexaner.items.ITEMS_ORDERED"
    }
}