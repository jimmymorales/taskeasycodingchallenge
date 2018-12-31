package jmlabs.com.freehourscodingchallenge

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import jmlabs.com.freehourscodingchallenge.model.MeetingsViewModel
import kotlinx.android.synthetic.main.dialog_add_meeting.view.*
import kotlinx.serialization.ImplicitReflectionSerializer

@ImplicitReflectionSerializer
class AddMeetingFragment : DialogFragment() {

    private lateinit var viewModel: MeetingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MeetingsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val view = LayoutInflater.from(it)
                .inflate(R.layout.dialog_add_meeting, null)

            val employeesAdapter = ArrayAdapter(
                context!!, android.R.layout.simple_spinner_item, viewModel.getEmployees())
            employeesAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
            view.spinnerEmployee.adapter = employeesAdapter

            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.add_meeting)
                .setView(view)
                .setPositiveButton("Save") { _, _ ->
                    val employee = view.spinnerEmployee.selectedItem as String
                    val hour = view.hoursSpinner.selectedItem as String
                    val minutes = view.minutesSpinner.selectedItem as String
                    val ampm = view.ampmSpinner.selectedItem as String
                    val time = hour +
                            (if(minutes.toInt()==0) "" else ":$minutes") +
                            ampm
                    try {
                        viewModel.addMeeting(employee, time)
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
