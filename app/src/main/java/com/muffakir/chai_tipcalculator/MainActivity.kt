package com.muffakir.chai_tipcalculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.muffakir.chai_tipcalculator.databinding.ActivityMainBinding

private const val INITIAL_TIP_PERCEENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.TvTipPercentLabel.text = "${INITIAL_TIP_PERCEENT}%"
        binding.SkeekBar.progress = INITIAL_TIP_PERCEENT

        binding.SkeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.TvTipPercentLabel.text = "${p1.toString()}%"
                computeTipandTotal()
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.EtBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("Changed", p0.toString())
                computeTipandTotal()
            }
        })


    }

    private fun updateTipDescription(p1: Int) {
        val tipDescription: String
        when (p1) {
            in 0..9 -> tipDescription = "Poor"
            in 10..14 -> tipDescription = "Acceptable"
            in 15..19 -> tipDescription = "Good"
            in 20..24 -> tipDescription = "Great"
            else -> tipDescription = "Amazing"
        }
        binding.tvResponse.text = tipDescription

        var color = ArgbEvaluator().evaluate(
            p1.toFloat() / binding.SkeekBar.max,
            ContextCompat.getColor(this, R.color.worstColor),
            ContextCompat.getColor(this, R.color.goodColor)
        ) as Int
        binding.tvResponse.setTextColor(color)
    }


    private fun computeTipandTotal() {
        if (binding.EtBaseAmount.text.isEmpty()) {
            binding.tvTipAmount.text = ""
            binding.tvTotalAmount.text = ""
            return
        }
        val baseAmount = binding.EtBaseAmount.text.toString().toDouble()
        val tipPercent = binding.SkeekBar.progress
        val tipAmount = baseAmount * tipPercent / 100
        val afterAmount = baseAmount + tipAmount

        binding.tvTipAmount.text = "%.2f".format(tipAmount)
        binding.tvTotalAmount.text = "%.2f".format(afterAmount)

    }
}