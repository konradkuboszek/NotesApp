package com.example.notesproject.framents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.notesproject.MainActivity
import com.example.notesproject.R
import com.example.notesproject.database.Category
import com.example.notesproject.databinding.FragmentHomeBinding
import com.example.notesproject.databinding.FragmentSettingsBinding
import com.example.notesproject.viewmodel.NoteViewModel


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var settingsBinding : FragmentSettingsBinding? = null
    private val binding get() = settingsBinding!!

    private lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsBinding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel

        val button : Button = binding.addCategoryButton

        button.setOnClickListener{
            addCategory()
        }

    }
    private fun addCategory(){

        val editText : EditText = binding.addCategoryEditText
        var categoryName : String = editText.text.toString()
        if(categoryName.isNotEmpty()){
            val category : Category = Category(0,categoryName)
            noteViewModel.insertCategory(category)
            editText.setText("")
        }
        else{
            Toast.makeText(context,"Enter name", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        settingsBinding = null
    }

}