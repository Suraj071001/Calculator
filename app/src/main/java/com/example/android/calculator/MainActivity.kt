package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.android.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "myTag"

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.tv0.setOnClickListener {
            updateScreen("0")
        }

        binding.tv1.setOnClickListener {
            updateScreen("1")
        }

        binding.tv2.setOnClickListener {
            updateScreen("2")
        }

        binding.tv3.setOnClickListener {
            updateScreen("3")
        }

        binding.tv4.setOnClickListener {
            updateScreen("4")
        }

        binding.tv5.setOnClickListener {
            updateScreen("5")
        }

        binding.tv6.setOnClickListener {
            updateScreen("6")
        }

        binding.tv7.setOnClickListener {
            updateScreen("7")
        }

        binding.tv8.setOnClickListener {
            updateScreen("8")
        }

        binding.tv9.setOnClickListener {
            updateScreen("9")
        }

        binding.tvPlus.setOnClickListener {
            if(!binding.tvScreen.text.toString().equals("0") || binding.tvScreen.text.toString().equals("")){
                updateScreen(" + ")
            }

        }

        binding.tvMinus.setOnClickListener {
            if(binding.tvScreen.text.toString().equals("0") || binding.tvScreen.text.toString().equals("")){
                updateScreen("-")
            }else{
                updateScreen(" - ")
            }

        }

        binding.tvDivide.setOnClickListener {
            if(!binding.tvScreen.text.toString().equals("0") || binding.tvScreen.text.toString().equals("")){
                updateScreen(" / ")
            }
        }

        binding.tvMultiply.setOnClickListener {
            if(!binding.tvScreen.text.toString().equals("0") || binding.tvScreen.text.toString().equals("")){
                updateScreen(" * ")
            }
        }

        binding.tvPercentage.setOnClickListener {
            if(!binding.tvScreen.text.toString().equals("0") || binding.tvScreen.text.toString().equals("")){
                updateScreen(" % ")
            }
        }

        binding.tvDecimal.setOnClickListener {
            if(!binding.tvScreen.text.contains(".")) updateScreen(".")
        }

        binding.tvClear.setOnClickListener {
            binding.tvScreen.text = "0"
        }

        binding.tvDelete.setOnClickListener {
            val currentText = binding.tvScreen.text.toString()
            val updatedText = currentText.removeRange(currentText.length-1,currentText.length)

            binding.tvScreen.text = updatedText
        }

        binding.tvEqual.setOnClickListener {

            var result = removeExtraSymbol(binding.tvScreen.text.toString())

            while(result.contains(" * ") || result.contains(" / ") || result.contains(" % ")
                || result.contains(" + ") || result.contains(" - ")){
                if(result.contains( " * ")) {

                    val list = modifiedString(result, " * ")

                    val ans = "${list[0]}${list[1].toDouble() * list[2].toDouble()}${list[3]}"
                    result = ans
                }
                else if (result.contains(" / ")){
                    val list = modifiedString(result, " / ")

                    val ans = "${list[0]}${list[1].toDouble() / list[2].toDouble()}${list[3]}"
                    result = ans
                }
                else if(result.contains(" + ")){
                    val list = modifiedString(result, " + ")

                    val ans = "${list[0]}${list[1].toDouble() + list[2].toDouble()}${list[3]}"
                    result = ans
                }
                else if(result.contains(" - ")){

                    val list = modifiedString(result, " - ")

                    val ans = "${list[0]}${list[1].toDouble() - list[2].toDouble()}${list[3]}"
                    result = ans
                }

            }

            binding.tvResult.text = result

        }

    }

    private fun checkAndDeleteZero(){
        if (binding.tvScreen.text.equals("0")){
            binding.tvScreen.text = ""
        }
    }

    private fun updateScreen(buttonText:String){

        checkAndDeleteZero()

        var updatedValue = binding.tvScreen.text.toString()

        if(buttonText == " * " || buttonText == " - " || buttonText == " + " || buttonText == " / " || buttonText == " % " ){
            updatedValue = removeExtraSymbol(updatedValue) + buttonText
        }else{
            updatedValue += buttonText
        }


        binding.tvScreen.text = updatedValue

    }

    private fun modifiedString(string:String, operator:String):List<String>{
        Log.d(TAG, "modifiedString: $string")
        val index = string.indexOf(operator)

        val startSub = string.substring(0 until index)
        val endSub = string.substring(index+3)

        val firstInt = startSub.substringAfterLast(" ")
        val secondInt = endSub.substringBefore(" ")

        return listOf(startSub.substringBeforeLast(firstInt),firstInt,secondInt,endSub.substringAfter(secondInt))
    }

    private fun removeExtraSymbol(string: String):String{
        var resultString = string

        var modString = ""
        if(resultString.length>2){
            modString = string.substring(string.length-3 until string.length)
        }

        if(modString == " * " || modString == " + " || modString == " - " || modString == " / " || modString == " % "){
            resultString = resultString.removeRange(resultString.length-3 until resultString.length)
        }

        return resultString
    }
}

