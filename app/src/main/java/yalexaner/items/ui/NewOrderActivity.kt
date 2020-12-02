package yalexaner.items.ui

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
        val itemsInput = binding.itemsInput
        val addItems = binding.addItems

        // enable button when there is a number
        itemsInput.doOnTextChanged { text, _, _, _ ->
            addItems.isEnabled = !text.isNullOrEmpty()
        }

        addItems.setOnClickListener {
            val items = itemsInput.text.toString().toInt()
            val replyIntent = Intent()

            replyIntent.putExtra(EXTRA_REPLY, items)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "yalexaner.items.REPLY"
    }
}