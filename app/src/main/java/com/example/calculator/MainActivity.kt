package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btn1.setOnClickListener { append("1", true) }
        binding.btn2.setOnClickListener { append("2", true) }
        binding.btn3.setOnClickListener { append("3", true) }
        binding.btn4.setOnClickListener { append("4", true) }
        binding.btn5.setOnClickListener { append("5", true) }
        binding.btn6.setOnClickListener { append("6", true) }
        binding.btn7.setOnClickListener { append("7", true) }
        binding.btn8.setOnClickListener { append("8", true) }
        binding.btn9.setOnClickListener { append("9", true) }
        binding.btn0.setOnClickListener { append("0", true) }

        binding.btnAdd.setOnClickListener { append("+", false) }
        binding.btnSubtract.setOnClickListener { append("-", false) }
        binding.btnMultiply.setOnClickListener { append("*", false) }
        binding.btnDivide.setOnClickListener { append("/", false) }
        binding.btnOpenBracket.setOnClickListener { append("(", false) }
        binding.btnCloseBracket.setOnClickListener { append(")", false) }
        binding.btnEqual.setOnClickListener { append("=", false) }

        binding.btnDot.setOnClickListener {
            val currentText = binding.txtCalculate.text.toString()
            // Check if the last character is a number and if the current number already contains a decimal point
            if (currentText.isNotEmpty() && currentText.last().isDigit() && !currentText.split("[+\\-*/()]".toRegex()).last().contains(".")) {
                append(".", true)
            }
        }

        binding.btnClear.setOnClickListener {
            binding.txtCalculate.text = ""
            binding.txtResult.text = ""
        }
        binding.btnback.setOnClickListener {
            val string = binding.txtCalculate.text.toString()
            if (string.isNotEmpty()) {
                binding.txtCalculate.text = string.substring(0, string.length - 1)
            }
        }
        binding.btnEqual.setOnClickListener {
            if (binding.txtCalculate.text.toString().isEmpty()) {
                binding.txtResult.text = "0"
            } else {
                try {
                    val expression = ExpressionBuilder(binding.txtCalculate.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) {
                        binding.txtResult.text = longResult.toString()
                    } else {
                        binding.txtResult.text = result.toString()
                    }

                } catch (e: Exception) {
                    binding.txtResult.text = "ERROR"
                }
            }
            binding.txtCalculate.text = ""
        }
    }

    private fun append(button: String, canClear: Boolean) {
        if (canClear) {
            binding.txtResult.text = ""
            binding.txtCalculate.append(button)
        } else {
            if (binding.txtResult.text.toString().contains("ERROR")) {
                binding.txtCalculate.append(button)
                binding.txtResult.text = ""
            } else {
                binding.txtCalculate.append(binding.txtResult.text)
                binding.txtCalculate.append(button)
                binding.txtResult.text = ""
            }
        }
    }
}