package com.example.QuizAPP

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.example.QuizAPP.databinding.FragmentStartBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class StartFragment : Fragment() {
    private var _binding:FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yy")
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment2_to_quizFragment)
        }
        binding.dateButton.setOnClickListener {
            val constrain = CalendarConstraints.Builder()
                .setOpenAt(calendar.timeInMillis)
                .build()

            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constrain)
                .setTitleText(resources.getString(R.string.date_dialog_title))
                .build()


            dateDialog.addOnPositiveButtonClickListener { timeMills ->
                calendar.timeInMillis = timeMills
                Snackbar.make(binding.dateButton,dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
            }
            dateDialog.show(parentFragmentManager,"DatePicker")
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}