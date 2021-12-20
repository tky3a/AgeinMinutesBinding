package binding.tutorial.ageinminutesbinding

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import binding.tutorial.ageinminutesbinding.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnDatePicker.setOnClickListener {
            clickDatePicker(it)
        }
    }

    private fun clickDatePicker(view: View) {
        // カレンダーインスタンス作成
        val myCalendar = Calendar.getInstance()
        // 現在年
        val year = myCalendar.get(Calendar.YEAR)
        // 現在月
        val month = myCalendar.get(Calendar.MONTH)
        // 現在日
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        println(year)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                // 内容が変更されたときに呼び出される(OK押下時)
                Toast.makeText(
                    this,
                    "選択した年:" + selectedYear + "月" + selectedMonth + "dayOfMonth" + selectedDayOfMonth,
                    Toast.LENGTH_LONG
                ).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.tvSelectedDate.text = selectedDate

                // フォーマット
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.JAPANESE)

                // 定義したフォーマットで文字列を解析してDateオブジェクトを生成
                val theDate = sdf.parse(selectedDate)
                println("----  ${theDate.time}")

                val selectedDateInMinutes = theDate!!.time / 60000
                // 現在の時間（ミリ秒単位）でフォーマット 日付オブジェクトが返ってくる
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                println("現在時間 $currentDate")

                val currentDateToMinutes = currentDate!!.time / 60000

                val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes

                binding.tvAgeInMinutes.text = differenceInMinutes.toString()
            }, year, month, day
        )

        // カレンダーの最大値を設定
        dpd.datePicker.maxDate = Date().time - 864000000
        dpd.show()
    }
}