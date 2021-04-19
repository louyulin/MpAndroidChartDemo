package com.mdl.mpandroidchartdemo

import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPieCHart()
        initLineChart()
        initBarChart()
        initCombinChart()
    }

    fun initPieCHart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(50f, 50f, 50f, 50f);
        pieChart.setCenterText("");//中间的文字
        pieChart.setDrawHoleEnabled(true);//中间的洞
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(50f); //中间的洞半径
//        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(false);//是否显示中间文字
        pieChart.setRotationAngle(0f);
        pieChart.setDrawSliceText(true);//是否显示文字
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        val legend = pieChart.legend
        legend.isEnabled = false
        setData(pieChart)
    }

    private fun setData(chart: PieChart) {
        val VORDIPLOM_COLORS1 = intArrayOf(
            Color.parseColor("#5460FE"),
            Color.parseColor("#9AAEFF"),
            Color.parseColor("#C6CBFF")
        )
        val parties = arrayOf(
            "Party A", "Party B", "Party C"
        )
        val entries = mutableListOf<PieEntry>()

        entries.add(
            PieEntry(
                50f, parties.get(0)
            )
        )

        entries.add(
            PieEntry(
                100f,
                parties.get(1)
            )
        )

        entries.add(
            PieEntry(
                150f,
                parties.get(2)
            )
        )

        val dataSet = PieDataSet(entries, "Election Results")
        //dataSet.setDrawValues(false)  //是否显示百分比
        dataSet.sliceSpace = 0f //缝隙
        dataSet.selectionShift = 5f //点击之后缩放

        val colors = mutableListOf<Int>()
        for (color in VORDIPLOM_COLORS1) {
            colors.add(color)
        }
        dataSet.colors = colors

        //是否有拓展线
        dataSet.valueLinePart1OffsetPercentage = 60f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.5f
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
//        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())//是否显示百分号
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        chart.setData(data)

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }

    fun initLineChart() {
        var speed = mutableListOf<Float>()
        speed.add(20f)
        speed.add(30f)
        speed.add(10f)
        speed.add(15f)
        speed.add(5f)
        lineChart.description.isEnabled = false
        lineChart.setPinchZoom(false)
        lineChart.setDrawGridBackground(false)
        lineChart.axisRight.isEnabled = false
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getLegend().setEnabled(false);
        // enable scaling and dragging
        lineChart.setDragEnabled(true)
        lineChart.setScaleEnabled(true)
        // create marker to display box when values are selected
        val mv = MyMarkerView(this, R.layout.custom_marker_view)
        lineChart.marker = mv
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(speed.size, true)
        xAxis.setDrawAxisLine(false)//x轴线取消
        val leftAxis = lineChart.axisLeft
        leftAxis.setDrawAxisLine(false)//左侧轴取消
        leftAxis.setDrawLabels(false)//左侧轴上面的值取消
        leftAxis.setLabelCount(5);
        leftAxis.setAxisMaximum(50f);

        var yVals = mutableListOf<Entry>()
        for (i in 0..speed.size - 1) {
            yVals.add(Entry(i.toFloat(), speed[i]))
        }
        val set = LineDataSet(yVals, "Data Set")
        set.setDrawValues(true)//是否在折现上显示值
        set.setDrawFilled(false)
        set.isHighlightEnabled = false //是否禁用点击高亮线
        set.highLightColor = Color.RED //设置点击交点后显示交高亮线的颜色
        val data = LineData(set)
        lineChart.data = data
        lineChart.invalidate()
        lineChart.animateY(800)
    }

    fun initBarChart() {
        barChart.getDescription().setEnabled(false)
        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(false)
        barChart.isScaleYEnabled = false //y轴缩放
        barChart.isScaleXEnabled = false//x轴缩放
        val l: Legend = barChart.getLegend()
        l.isEnabled = false
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false)
        xAxis.axisMinimum = 0f

        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 35f
        barChart.getAxisRight().setEnabled(false)
        leftAxis.setAxisMaxValue(10f)//y轴设置最大值
        leftAxis.axisMinimum = 0f

        val mLabels = arrayOf(
            "甲骨文", "海康", "大华", "飞翔", "阿里巴巴", "网易", "百度", "谷歌",
            "甲骨文", "海康", "大华", "飞翔", "阿里巴巴", "网易", "百度", "谷歌"
        )
        barChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(mLabels));
        barChart.getXAxis().setLabelCount(mLabels.size);

        var list1 = mutableListOf<BarEntry>()
        var list2 = mutableListOf<BarEntry>()


        for (i in 0..15) {
            list1.add(BarEntry(i.toFloat(), (Math.random() * 10).toFloat()))
            list2.add(BarEntry(i.toFloat(), (Math.random() * 10).toFloat()))
        }


        val matrix = Matrix()
        matrix.postScale((list1.size / 5).toFloat(), 1f) //缩放
        barChart.viewPortHandler.refresh(matrix, barChart, false)

        var set1 = BarDataSet(list1, "type1")
        set1.color = Color.parseColor("#5460FE")
        var set2 = BarDataSet(list2, "type2")
        set2.color = Color.parseColor("#6D75A2")

        val data = BarData(set1, set2)
        data.isHighlightEnabled = false//高亮
        barChart.setData(data)
        barChart.getBarData().setBarWidth(0.3f)


        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(
            0f + barChart.getBarData().getGroupWidth(0.2f, 0.1f) * list1.size
        )
        barChart.groupBars(0f, 0.2f, 0.1f)
        barChart.invalidate()
    }

    fun initCombinChart() {
        combineChart.description.isEnabled = false
        combineChart.setDrawGridBackground(false)
        combineChart.setDrawBarShadow(false)
        combineChart.setHighlightFullBarEnabled(false)
        // draw bars behind lines
        combineChart.setDrawOrder(
            arrayOf(DrawOrder.BAR, DrawOrder.LINE)
        )
        combineChart.legend.isEnabled = false
        combineChart.isScaleYEnabled = false //y轴缩放
        combineChart.isScaleXEnabled = false//x轴缩放
        val axisRight = combineChart.axisRight
        axisRight.isEnabled = false
        val axisLeft = combineChart.axisLeft
        axisLeft.setDrawGridLines(false)
        axisLeft.axisMinimum = 0f
        val xAxis = combineChart.xAxis
        xAxis.axisMinimum = -0.5f //左右留出0.5保证显示完整
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        val mLabels = arrayOf(
            "1月1日" , "1月2日" , "1月3日" , "1月4日" , "1月5日"
        )
        combineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(mLabels));



        val combinedData = CombinedData()
        combinedData.setData(generateLineData())
        combinedData.setData(generateBarData())
        combineChart.data = combinedData
        xAxis.axisMaximum = (combinedData.xMax + 0.5).toFloat()
        combineChart.invalidate()

    }

    fun generateLineData(): LineData {
        val lineData = LineData()
        var entries = mutableListOf<Entry>()
        for (i in 0..4) {
            entries.add(Entry(i.toFloat(), (Math.random() * 10).toFloat()))
        }

        val set = LineDataSet(entries, "Line DataSet")
        set.setColor(Color.parseColor("#5460FE"))
        set.setLineWidth(2.5f)
        set.setCircleColor(Color.parseColor("#5460FE"))
        set.setCircleRadius(5f)
//        set.setFillColor(Color.rgb(240, 238, 70))
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER)
        set.setDrawValues(true)
        set.setValueTextSize(10f)
        set.setValueTextColor(Color.BLACK)
        set.axisDependency = YAxis.AxisDependency.LEFT
        lineData.addDataSet(set)
        lineData.isHighlightEnabled =false
        return lineData
    }

    fun generateBarData(): BarData {
        val entries1 = mutableListOf<BarEntry>()
        for (i in 0..4) {
            entries1.add(BarEntry(i.toFloat(), (Math.random() * 10).toFloat()))
        }
        val set1 = BarDataSet(entries1, "Bar 1")
        set1.color = Color.parseColor("#EBEEFA")
        set1.valueTextColor = Color.TRANSPARENT
        set1.valueTextSize = 10f
        set1.axisDependency = YAxis.AxisDependency.LEFT

        val barData = BarData(set1)
        barData.isHighlightEnabled =false
        barData.setBarWidth(0.5f)
        return barData
    }
}