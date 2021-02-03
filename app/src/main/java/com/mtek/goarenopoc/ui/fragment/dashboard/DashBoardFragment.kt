package com.mtek.goarenopoc.ui.fragment.dashboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import com.mtek.goarenopoc.databinding.FragmentDashBoardBinding
import com.mtek.goarenopoc.utils.Constants
import com.mtek.goarenopoc.utils.flag_error
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.ColumnChartView
import lecho.lib.hellocharts.view.LineChartView

class DashBoardFragment :
    BaseFragment<FragmentDashBoardBinding, DashboardViewModel>(DashboardViewModel::class) {

    private val yValueGroup1: ArrayList<BarEntry> = arrayListOf()
    private val yValueGroup2: ArrayList<BarEntry> = arrayListOf()
    private val xAxisValues: ArrayList<String> = arrayListOf()

    private val observerMontlySales: Observer<DashboardResponseModel> = Observer {
        if (it != null) {
            Log.e("Donen Hedef Response", "${it.data}")
            val dataSales = it.data?.groupBy { productGroup ->
                productGroup.product_group
            }
            for (i in dataSales!!) {
                xAxisValues.add(i.key)
                var amounts: Int = 0
                for (j in i.value) {
                    amounts += j.quantity
                }
                yValueGroup2.add(BarEntry(1f, floatArrayOf(1.toFloat(), amounts.toFloat())))
            }
            populateGraphData()
        }
    }

    private val observerTarget: Observer<ExpectionResponseModel> = Observer {
        if (it != null) {
            if (it.data!![0].user?.employee_type == "MANAGER")viewModel.getMontlyTargetById(7) else viewModel.getMontlyTarget()
            val dataTarget = it.data.groupBy { expection ->
                expection.product_group
            }
            for (i in dataTarget) {
                xAxisValues.add(i.key)
                var quantity = 0
                for (j in i.value) {
                    quantity += j.quantity
                }
                yValueGroup1.add(BarEntry(1f, floatArrayOf(1.toFloat(), quantity.toFloat())))
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            getMontlySales()
            getMontlyTarget()
            monthlySales.observe(viewLifecycleOwner, observerMontlySales)
            responseTarget.observe(viewLifecycleOwner, observerTarget)
        }

    }

    private fun populateGraphData() {

        val barDataSet1 = BarDataSet(yValueGroup1, "")
        barDataSet1.color = Color.BLUE
        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        val barDataSet2 = BarDataSet(yValueGroup2, "")
        barDataSet2.color = Color.YELLOW
        barDataSet2.setDrawIcons(false)
        barDataSet2.setDrawValues(false)

        val barData = BarData(barDataSet1, barDataSet2)

        val legend = binding.barChartView.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        val legenedEntries = arrayListOf<LegendEntry>()

        legenedEntries.add(
            LegendEntry(
                "Hedeflenen",
                Legend.LegendForm.SQUARE,
                8f,
                8f,
                null,
                Color.BLUE
            )
        )
        legenedEntries.add(
            LegendEntry("Gerçekleşen", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.YELLOW)
        )

        legend.setCustom(legenedEntries)

        legend.yOffset = 2f
        legend.xOffset = 2f
        legend.yEntrySpace = 0f
        legend.textSize = 10f

        val xAxis = binding.barChartView.xAxis
        xAxis.setLabelCount(6, true)
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        xAxis.textSize = 15f
        xAxis.setDrawGridLines(false)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        binding.barChartView.isDragEnabled = true
        binding.barChartView.axisRight.isEnabled = false
        binding.barChartView.setScaleEnabled(true)

        //Soldaki alan
        val leftAxis = binding.barChartView.axisLeft
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(false) // ekrani kare / dikdortgen seklinde ayirior grid olarak hersey daha net
        leftAxis.spaceTop = 1f
        leftAxis.axisMinimum = 0f

        binding.barChartView.data = barData

        binding.barChartView.barData.barWidth = 0.4f
        binding.barChartView.xAxis.axisMaximum =
            0f + binding.barChartView.barData.getGroupWidth(0.1f, 0.05f) * 3 //kac grup yazin varsa
        binding.barChartView.xAxis.axisMinimum = 0f
        binding.barChartView.description.text = ""
        binding.barChartView.xAxis.axisMaximum = 3f
        binding.barChartView.groupBars(0f, 0.1f, 0.05f)
        binding.barChartView.data.isHighlightEnabled = false
        binding.barChartView.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        binding.barChartView.invalidate()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        flag_error("onAttach Dashboard")
    }

    override fun onDetach() {
        super.onDetach()
        flag_error("onDetach Dashboard")
    }

    override fun getViewBinding() = FragmentDashBoardBinding.inflate(layoutInflater)


}

/*
       private var montlySalesList: ArrayList<DashBoardDataModel>? = arrayListOf()
    private val months = arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
    private val days = arrayListOf("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun")
    private var lineData: LineChartData? = null
    private var columnData: ColumnChartData? = null

   private fun generateInitialLineData() {
        val numValues = 6
        val axisValues: ArrayList<AxisValue> = arrayListOf()
        val values: ArrayList<PointValue> = arrayListOf()

        for (i in 0..numValues) {
            values.add(PointValue(i.toFloat(), 0f))
            axisValues.add(AxisValue(i.toFloat()).setLabel(days[i]))
        }

        val line = Line(values)
        line.setColor(ChartUtils.COLOR_GREEN).isCubic = true

        val lines: ArrayList<Line> = arrayListOf()
        lines.add(line)

        lineData = LineChartData(lines)
        lineData!!.axisXBottom = Axis(axisValues).setHasLines(true)
        lineData!!.axisYLeft = Axis().setHasLines(true).setMaxLabelChars(3)

        binding.chartView.lineChartData = lineData

        binding.chartView.isViewportCalculationEnabled = false

        val v = Viewport(0f, 110f, 6f, 0f)
        binding.chartView.maximumViewport = v
        binding.chartView.currentViewport = v

        binding.chartView.zoomType = ZoomType.HORIZONTAL
    }

    private fun populateGraphData() {

        val groupSpace = 0.1f // her 2 item arasi uzaklik

        val xAxisValues = ArrayList<String>()
        xAxisValues.add("Aksesuar")
        xAxisValues.add("Telko")
        xAxisValues.add("Cihaz")

        val yValueGroup1 = ArrayList<BarEntry>()
        val yValueGroup2 = ArrayList<BarEntry>()

        val barDataSet1: BarDataSet
        val barDataSet2: BarDataSet


        yValueGroup1.add(BarEntry(1f, floatArrayOf(9.toFloat(), 3.toFloat())))
        yValueGroup2.add(BarEntry(1f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yValueGroup1.add(BarEntry(2f, floatArrayOf(3.toFloat(), 3.toFloat())))
        yValueGroup2.add(BarEntry(2f, floatArrayOf(4.toFloat(), 15.toFloat())))

        yValueGroup1.add(BarEntry(3f, floatArrayOf(3.toFloat(), 3.toFloat())))
        yValueGroup2.add(BarEntry(3f, floatArrayOf(4.toFloat(), 15.toFloat())))


        barDataSet1 = BarDataSet(yValueGroup1, "")
        barDataSet1.color = Color.LTGRAY
        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        barDataSet2 = BarDataSet(yValueGroup2, "")
        barDataSet2.color = Color.YELLOW
        barDataSet2.setDrawIcons(false)
        barDataSet2.setDrawValues(false)

        val barData = BarData(barDataSet1, barDataSet2)

        binding.barChart.description.isEnabled = false
        binding.barChart.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        binding.barChart.data = barData
        binding.barChart.xAxis.axisMinimum = 0f
        binding.barChart.xAxis.axisMaximum = 12f
        binding.barChart.setFitBars(true)
        binding.barChart.data.isHighlightEnabled = false
        binding.barChart.animateXY(1500, 1500)
        binding.barChart.invalidate()

        // set bar label
        val legend = binding.barChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        val legenedEntries = arrayListOf<LegendEntry>()

        legenedEntries.add(
            LegendEntry(
                "Hedeflenen",
                Legend.LegendForm.SQUARE,
                8f,
                8f,
                null,
                Color.LTGRAY
            )
        )
        legenedEntries.add(
            LegendEntry("Gerçekleşen", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.YELLOW)
        )

        legend.setCustom(legenedEntries)

        legend.yOffset = 2f
        legend.xOffset = 2f
        legend.yEntrySpace = 0f
        legend.textSize = 10f

        val xAxis = binding.barChart.xAxis
        xAxis.setLabelCount(6, true)
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.yOffset = 2f
        xAxis.xOffset = 2f
        xAxis.textSize = 5f
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 12f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        xAxis.labelCount = 12
        xAxis.xOffset = 1f
        xAxis.yOffset = 10f
        xAxis.mAxisMaximum = 12f
        xAxis.mLabelWidth = 15
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 4f
        xAxis.spaceMax = 4f

        binding.barChart.setVisibleXRangeMaximum(12f)
        binding.barChart.setVisibleXRangeMinimum(12f)
        binding.barChart.isDragEnabled = true
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.setScaleEnabled(true)

        val leftAxis = binding.barChart.axisLeft
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(false) // ekrani kare / dikdortgen seklinde ayirior grid olarak hersey daha net
        leftAxis.spaceTop = 1f
        leftAxis.axisMinimum = 0f

        binding.barChart.data = barData
        binding.barChart.setVisibleXRange(1f, 12f)

        if (xAxisValues.count() == 6) {
            binding.barChart.barData.barWidth = 0.11f
            binding.barChart.xAxis.axisMaximum =
                0f + binding.barChart.barData.getGroupWidth(0.4f, 0.1f) * 15
            binding.barChart.xAxis.axisMinimum = 0f
            binding.barChart.xAxis.axisMaximum = 6f
            binding.barChart.groupBars(0f, 0.4f, 0f) // 2'li barlar arasindaki uzaklik
            binding.barChart.data.isHighlightEnabled = false
            binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        } else {
            binding.barChart.barData.barWidth = 0.11f
            binding.barChart.xAxis.axisMaximum =
                0f + binding.barChart.barData.getGroupWidth(0.4f, 0.03f) * 7
            binding.barChart.xAxis.axisMinimum = 0f
            binding.barChart.xAxis.axisMaximum = 7f
            binding.barChart.groupBars(0f, 0.65f, 0.07f)
            binding.barChart.data.isHighlightEnabled = false
            binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        }
        binding.barChart.invalidate()
    }*/