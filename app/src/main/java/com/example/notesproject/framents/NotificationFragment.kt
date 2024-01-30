package com.example.notesproject.framents

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.R
import com.example.notesproject.database.Note
import com.example.notesproject.databinding.FragmentEditNoteBinding
import com.example.notesproject.databinding.FragmentHomeBinding
import com.example.notesproject.databinding.FragmentNotificationBinding
import com.example.notesproject.model.Notification
import com.example.notesproject.model.channelID
import com.example.notesproject.model.messageExtra
import com.example.notesproject.model.notificationID
import com.example.notesproject.model.titleExtra
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : BottomSheetDialogFragment() {
    private val args: EditNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private var notificationBinding: FragmentNotificationBinding? = null
    private val binding get() = notificationBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        notificationBinding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentNote = args.note!!
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()
        binding.setNotificationButton.setOnClickListener{
            scheduleNotification()
            findNavController().popBackStack()
        }
        binding.cancelNotificationButton.setOnClickListener{
            findNavController().popBackStack()
        }


    }
    private fun getTime(): Long{
        val minute = binding.notificationTimePicker.minute
        val hour = binding.notificationTimePicker.hour
        val day = binding.notificationDatePicker.dayOfMonth
        val month = binding.notificationDatePicker.month
        val year = binding.notificationDatePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)
        return calendar.timeInMillis
    }


    private fun scheduleNotification(){
        val intent = Intent(context, Notification::class.java)
        val title = currentNote.noteTitle
        val message = currentNote.noteContent
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent)
    }
    private fun createNotificationChannel(){
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = desc
        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}