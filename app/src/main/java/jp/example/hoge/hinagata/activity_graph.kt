package jp.example.hoge.hinagata

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.charts.LineChart
import kotlinx.android.synthetic.main.activity_local_data.*
import java.util.*

// o. MPAndroidChart グラフ表示
class activity_graph  : AppCompatActivity() {
    val dataCount : Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // メニュー
        setSupportActionBar(temp_toolbar)
        temp_toolbar.setTitle(R.string.main_title)
        temp_toolbar.setTitleTextColor(Color.WHITE)
        temp_toolbar.setSubtitle(R.string.sied_menu_item1)
        temp_toolbar.setSubtitleTextColor(Color.WHITE)

    }

    fun onGraphDisp(v:View ) {
        gView()
    }

    // 折れ線グラフ
    fun gView() {
        // グラフのデータを作成
        val line1_values : ArrayList<Entry> = lineCreateDummyData(dataCount+5, 3, 15)
        val line1 : LineDataSet = LineDataSet(line1_values,"line-1")
        line1.setColor(Color.BLACK)
        line1.setLineWidth(1.5f)
        line1.setDrawCircles(false)
        //
        val line2_values : ArrayList<Entry> = lineCreateDummyData(dataCount, 0, 10)
        val line2 : LineDataSet = LineDataSet(line2_values,"line-2")
        line2.setColor(Color.RED)
        line2.setLineWidth(1.5f)
        line2.setDrawCircles(false)
        //
        var dataSets : List<LineDataSet> =  ArrayList<LineDataSet>()
        dataSets += line1
        dataSets += line2
        // 折れ線グラグのデータ
        val lineGraphData : LineData = LineData( dataSets )

        // 画面(html)の　line_chart にグラフの表示
        val mChart: LineChart = findViewById(R.id.line_chart)

//        mChart.setBackgroundColor(Color.GREEN) // グラフの外の色
        mChart.setDrawGridBackground(true)
        mChart.getDescription().setEnabled(true)


        // Grid縦軸を破線
        val xs : XAxis = mChart.getXAxis()
        xs.enableGridDashedLine(10f, 10f, 0f);

        xs.setPosition(XAxis.XAxisPosition.BOTTOM)  // プロット位置

        //　
        val leftAxis : YAxis = mChart.getAxisLeft()
        // Y軸　最大/最小 設定
        leftAxis.setAxisMaximum(20f)
        leftAxis.setAxisMinimum(0f)

        // Grid横軸を破線
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(true)
        // 右側の目盛り 非表示
        mChart.getAxisRight().setEnabled(false)

        // グラフデータを画面にセット
        mChart.data =lineGraphData
        mChart.invalidate()
    }

    // ダミーデータの作成
    fun lineCreateDummyData(count : Int, minValue : Int, maxValue : Int ) : ArrayList<Entry> {
        // 乱数
        val random : Random = Random()
        // 乱数の発生範囲
        val occurrenceRange : Int = maxValue - minValue
        // 座標データ
        val rLineData : ArrayList<Entry> = ArrayList<Entry>()
        for( i : Int in 0..count) {
            // Y軸乱数で発生させて ( 開始時は０ )
            val line_rx : Float = if ( 0 == i ) 0.0F else random.nextInt(occurrenceRange).toFloat()+minValue.toFloat()
            // rLineDataに入れる ( x, y )
            rLineData.add(Entry( i.toFloat(), line_rx ))
        }
        return rLineData
    }

}
