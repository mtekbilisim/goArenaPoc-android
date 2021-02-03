package com.mtek.goarenopoc.ui.fragment.dashboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.DashboardDataModel
import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import com.mtek.goarenopoc.data.network.response.SalesResponseModel
import com.mtek.goarenopoc.databinding.FragmentDashBoardBinding
import com.mtek.goarenopoc.utils.flag_error
import com.mtek.goarenopoc.utils.manager.UserManager

class DashBoardFragment :
    BaseFragment<FragmentDashBoardBinding, DashboardViewModel>(DashboardViewModel::class) {

    private val yValueGroup1: ArrayList<BarEntry> = arrayListOf()
    private val yValueGroup2: ArrayList<BarEntry> = arrayListOf()
    private val xAxisValues: ArrayList<String> = arrayListOf()

    private val yQualityGroup1: ArrayList<BarEntry> = arrayListOf()
    private val yQualityGroup2: ArrayList<BarEntry> = arrayListOf()
    private val xAxisQualityValues: ArrayList<String> = arrayListOf()

    private var adapter: BaseAdapter<DashboardDataModel>? = null
    private var personelDataList: ArrayList<DashboardDataModel> = arrayListOf()

    private val observerSales: Observer<SalesResponseModel> = Observer {
        if (it != null) {
            for (i in it.data!!) {
                xAxisValues.add(i.product_group)
                yValueGroup2.add(BarEntry(1f, floatArrayOf(1.toFloat(), i.expectation.toFloat())))
                yValueGroup1.add(BarEntry(1f, floatArrayOf(1.toFloat(), i.sales.toFloat())))
            }
            populateGraphData()
        }
    }

    private val observerPersonelStatistic: Observer<SalesResponseModel> = Observer {
        if (it != null) {
            for (i in it.data!!) {
                if (i.employee?.contains("Adem")!!){
                    xAxisQualityValues.add("Ocak")
                }else if(i.employee?.contains("Ahmet")!!){
                    xAxisQualityValues.add("Şubat")
                }else if(i.employee?.contains("Mehmet")!!){
                    xAxisQualityValues.add("Mart")
                }

                yQualityGroup2.add(BarEntry(1f, floatArrayOf(1.toFloat(), i.expectation.toFloat())))
                yQualityGroup1.add(BarEntry(1f, floatArrayOf(1.toFloat(), i.sales.toFloat())))
            }
            PersonelGraphData()
        }
    }

    private val observerMontlySales: Observer<DashboardResponseModel> = Observer {
        if (it != null) {
            personelDataList = it.data as ArrayList<DashboardDataModel>
            setAdapterData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            getSalesAndTarget(13)
            getMontlySales()
            UserManager.instance.user?.shopId?.let { getPersonSalesAndTarget(it) }
            responsePersonelStatistic.observe(viewLifecycleOwner, observerPersonelStatistic)
            responseSales.observe(viewLifecycleOwner, observerSales)
            monthlySales.observe(viewLifecycleOwner, observerMontlySales)
        }
    }

    private fun setAdapterData() {
        adapter =
            BaseAdapter(
                requireContext(),
                R.layout.row_item_dashbroard_data_grid,
                personelDataList
            ) { v, item, position ->
                val txtProductGroup = v!!.findViewById(R.id.txtProductGroup) as TextView
                val txtName = v.findViewById(R.id.txtTitle) as TextView
                val txtProduct = v.findViewById(R.id.txtProduct) as TextView
                val txtSell = v.findViewById(R.id.txtSell) as TextView

                txtName.text = item.user?.first_name
                txtProductGroup.text = item.product_group
                txtProduct.text = item.product
                txtSell.text = item.quantity.toString()
            }
        binding.recyclerView.adapter = adapter
    }

    private fun populateGraphData() {

        val barDataSet1 = BarDataSet(yValueGroup1, "")
        barDataSet1.color = Color.BLUE
        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        val barDataSet2 = BarDataSet(yValueGroup2, "")
        barDataSet2.color = Color.RED
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
            LegendEntry("Gerçekleşen", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.RED)
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

    private fun PersonelGraphData() {

        val barDataSet1 = BarDataSet(yQualityGroup1, "")
        barDataSet1.color = Color.LTGRAY
        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        val barDataSet2 = BarDataSet(yQualityGroup2, "")
        barDataSet2.color = Color.YELLOW
        barDataSet2.setDrawIcons(false)
        barDataSet2.setDrawValues(false)

        val barData = BarData(barDataSet1, barDataSet2)

        val legend = binding.barChartStaff.legend
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
        legend.textColor = Color.WHITE
        legend.xOffset = 2f
        legend.yEntrySpace = 0f
        legend.textSize = 10f

        val xAxis = binding.barChartStaff.xAxis
        xAxis.setLabelCount(6, true)
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        xAxis.textSize = 15f
        xAxis.setDrawGridLines(false)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisQualityValues)
        xAxis.textColor = Color.WHITE
        binding.barChartStaff.isDragEnabled = true
        binding.barChartStaff.axisRight.isEnabled = false
        binding.barChartStaff.setScaleEnabled(true)

        //Soldaki alan
        val leftAxis = binding.barChartStaff.axisLeft
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 1f
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMinimum = 0f

        binding.barChartStaff.data = barData

        binding.barChartStaff.barData.barWidth = 0.4f
        binding.barChartStaff.xAxis.axisMaximum =
            0f + binding.barChartStaff.barData.getGroupWidth(0.1f, 0.05f) * 3
        binding.barChartStaff.xAxis.axisMinimum = 0f
        binding.barChartStaff.description.text = ""
        binding.barChartStaff.xAxis.axisMaximum = 3f
        binding.barChartStaff.groupBars(0f, 0.1f, 0.05f)
        binding.barChartStaff.data.isHighlightEnabled = false
        binding.barChartStaff.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisQualityValues)

        binding.barChartStaff.invalidate()

    }

    override fun getViewBinding() = FragmentDashBoardBinding.inflate(layoutInflater)


}