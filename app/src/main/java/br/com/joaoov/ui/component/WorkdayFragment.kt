package br.com.joaoov.ui.component

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_workday.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class WorkdayFragment : Fragment(R.layout.fragment_workday) {

    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val adapter by lazy { DayOfWeekAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        componentViewModel.withComponents = Components(path = false)
        setupView()
    }

    private fun setupView() {
        setupButtonFinish()
        setupCancelButton()
        setupRecyclerView()
        setupTimePickerStart()
        setupTimePickerEnd()
        setupTimePickerStartLunch()
        setupTimePickerEndLunch()
    }

    private fun setupRecyclerView() {
        recyclerViewWorkday.adapter = this.adapter
    }

    private fun setupTimePickerStart() {
        timePickerStart.apply {
            setIs24HourView(true)
            setHourValue(8)
            setMinuteValue(0)
        }
    }

    private fun setupTimePickerEnd() {
        timePickerEnd.apply {
            setIs24HourView(true)
            setHourValue(12)
            setMinuteValue(0)
        }
    }

    private fun setupTimePickerStartLunch() {
        timePickerStartLunch.apply {
            setIs24HourView(true)
            setHourValue(13)
            setMinuteValue(12)
        }
    }

    private fun setupTimePickerEndLunch() {
        timePickerLunchEnd.apply {
            setIs24HourView(true)
            setHourValue(18)
            setMinuteValue(0)
        }
    }

    private fun setupButtonFinish() {
        buttonFinish.setOnClickListener {
            val formatedDaysOfWeek = adapter.getFormatedValeus()
            val startTime = timePickerStart.getFormatedTime()
            val endTime = timePickerEnd.getFormatedTime()
            val startLunchTime = timePickerStartLunch.getFormatedTime()
            val endLunchTime = timePickerLunchEnd.getFormatedTime()
            val formatedWorkTime =
                formatWorkDay(formatedDaysOfWeek, startTime, endTime, startLunchTime, endLunchTime)

            setFragmentResult(REQUEST_KEY, bundleOf(RESULT_KEY to formatedWorkTime))
            findNavController().navigateUp()
        }
    }


    private fun setupCancelButton() {
        buttonCancel.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(RESULT_KEY to ""))
            findNavController().navigateUp()
        }
    }

    private fun formatWorkDay(
        formatedDaysOfWeek: String,
        startTime: String,
        endTime: String,
        startLunchTime: String,
        endLunchTime: String
    ) =
        "$formatedDaysOfWeek. $startTime-$endTime $startLunchTime-$endLunchTime"

    companion object {
        const val REQUEST_KEY = "WORKDAY_REQUEST"
        const val RESULT_KEY = "WORKDAY_RESULT"
    }

}