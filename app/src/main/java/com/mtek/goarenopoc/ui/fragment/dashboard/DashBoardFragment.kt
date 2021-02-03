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

    private val observerMontlySales: Observer<DashboardResponseModel> = Observer {
        if (it != null) {
            yValueGroup2.clear()
            val dataSales = it.data!!.groupBy { productGroup ->
                productGroup.product_group
            }
            for (i in dataSales) {
                xAxisValues.add(i.key)
                var amounts: Int = 0
                for (j in i.value) {
                    amounts += j.quantity
                }

                yValueGroup2.add(BarEntry(1f, floatArrayOf(1.toFloat(), amounts.toFloat())))
            }
            populateGraphData()
            personelDataList = it.data as ArrayList<DashboardDataModel>
            setAdapterData()
        }

    }

    private val observerTarget: Observer<ExpectionResponseModel> = Observer {
        if (!it.data.isNullOrEmpty()) {
            yValueGroup1.clear()
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

            val shopQualityPerson = it.data.groupBy { shopPerson ->
                shopPerson.user?.first_name
            }
            for (k in shopQualityPerson) {
                k.key?.let { it1 -> xAxisQualityValues.add(it1) }
                var personTarget = 0
                for (x in k.value) {
                    personTarget += x.quantity
                }
                yQualityGroup1.add(BarEntry(1f, floatArrayOf(1.toFloat(), personTarget.toFloat())))
            }
        }
    }

    private val observerShopQuality: Observer<DashboardResponseModel> = Observer {
        if (!it.data.isNullOrEmpty()) {

            val dataQuality = it.data.groupBy { shopPerson ->
                shopPerson.user?.first_name
            }
            for (i in dataQuality) {
                    i.key?.let { it1 -> xAxisQualityValues.add(it1) }
                    var personQuality = 0
                    for (j in i.value) {
                        personQuality += j.quantity
                    }

                    yQualityGroup2.add(BarEntry(1f, floatArrayOf(2.toFloat(), personQuality.toFloat())))
                }
            PersonelGraphData()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            monthlySales.observe(viewLifecycleOwner, observerMontlySales)
            responseTarget.observe(viewLifecycleOwner, observerTarget)
            responseShopQuailty.observe(viewLifecycleOwner, observerShopQuality)
            getFilterByShopPersonel(3)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel {

            getMontlySales()
            if (UserManager.instance.user?.employee_type == "MANAGER") getMontlyTargetById(3) else getMontlyTarget()

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

    private fun PersonelGraphData() {

        val barDataSet1 = BarDataSet(yQualityGroup1, "")
        barDataSet1.color = Color.BLUE
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
                Color.BLUE
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