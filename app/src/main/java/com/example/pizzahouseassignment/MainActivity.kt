package com.example.pizzahouseassignment

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Debug tag for logging
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        val spinner: Spinner = findViewById(R.id.pizza_spinner)
        val toppingsLabel: TextView = findViewById(R.id.toppings_label)
        val toppingsContainer: LinearLayout = findViewById(R.id.toppings_container)
        val showPriceButtonCustom: Button = findViewById(R.id.btn_show_price_custom)
        val showPriceButtonFeast: Button = findViewById(R.id.btn_show_price_feast)
        val priceDisplay: TextView = findViewById(R.id.price_display)
        val pizzaTypeSpinner: Spinner = findViewById(R.id.pizza_type_spinner) // New spinner for pizza types

        // List of checkboxes for toppings
        val checkBoxes = listOf(
            findViewById<CheckBox>(R.id.checkbox_pepperoni),
            findViewById<CheckBox>(R.id.checkbox_mushrooms),
            findViewById<CheckBox>(R.id.checkbox_onions),
            findViewById<CheckBox>(R.id.checkbox_sausage),
            findViewById<CheckBox>(R.id.checkbox_bacon),
            findViewById<CheckBox>(R.id.checkbox_extra_cheese)
        )

        // Set spinner item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> { // Make your own pizza selected
                        toppingsLabel.visibility = View.VISIBLE
                        toppingsContainer.visibility = View.VISIBLE
                        pizzaTypeSpinner.visibility = View.GONE // Hide pizza type spinner
                        showPriceButtonCustom.visibility = View.VISIBLE
                        showPriceButtonFeast.visibility = View.GONE
                        priceDisplay.visibility = View.GONE
                    }
                    2 -> { // Feast Pizza selected
                        toppingsLabel.visibility = View.GONE
                        toppingsContainer.visibility = View.GONE
                        pizzaTypeSpinner.visibility = View.VISIBLE // Show pizza type spinner
                        showPriceButtonCustom.visibility = View.GONE
                        showPriceButtonFeast.visibility = View.VISIBLE
                        priceDisplay.visibility = View.GONE
                    }
                    else -> { // Other options selected
                        toppingsLabel.visibility = View.GONE
                        toppingsContainer.visibility = View.GONE
                        pizzaTypeSpinner.visibility = View.GONE // Hide pizza type spinner
                        showPriceButtonCustom.visibility = View.GONE
                        showPriceButtonFeast.visibility = View.GONE
                        priceDisplay.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }

        // Array of pizza types for Feast Pizza option
        val pizzaTypeOptions = arrayOf("Select Pizza", "Metza", "Hawaiian", "Extravaganza", "Pepperoni", "Veggie")
        pizzaTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pizzaTypeOptions)

        // Set pizza type spinner item selected listener
        pizzaTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Show price button when a pizza type is selected
                showPriceButtonFeast.visibility = if (position > 0) View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }

        // Set show price button click listener for Custom Pizza
        showPriceButtonCustom.setOnClickListener {
            val selectedToppings = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
            if (selectedToppings.isEmpty()) {
                Toast.makeText(this, "Please select at least one topping", Toast.LENGTH_SHORT).show()
            } else {
                val price = calculatePrice(selectedToppings)
                val displayText = "Toppings:\n${selectedToppings.joinToString("\n")}\n\nPrice: $${String.format("%.2f", price)}"
                priceDisplay.text = displayText
                priceDisplay.visibility = View.VISIBLE
            }
        }

        // Set show price button click listener for Feast Pizza
        showPriceButtonFeast.setOnClickListener {
            val selectedPizzaType = pizzaTypeOptions[pizzaTypeSpinner.selectedItemPosition]
            if (selectedPizzaType == "Select Pizza") {
                Toast.makeText(this, "Please select a pizza type", Toast.LENGTH_SHORT).show()
            } else {
                // Assuming direct price for simplicity
                val price = when (selectedPizzaType) {
                    "Metza" -> 12.99
                    "Hawaiian" -> 11.99
                    "Extravaganza" -> 14.99
                    "Pepperoni" -> 10.99
                    "Veggie" -> 11.99
                    else -> 0.0
                }
                val displayText = "Pizza Type: $selectedPizzaType\nPrice: $${String.format("%.2f", price)}"
                priceDisplay.text = displayText
                priceDisplay.visibility = View.VISIBLE
            }
        }
    }

    // Calculate price based on selected toppings
    private fun calculatePrice(toppings: List<String>): Double {
        val basePrice = 5.0
        val toppingPrice = 1.5
        return basePrice + (toppingPrice * toppings.size)
    }
}
