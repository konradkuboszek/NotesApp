package com.example.notesproject.framents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.notesproject.MainActivity
import com.example.notesproject.R
import com.example.notesproject.database.Note
import com.example.notesproject.databinding.FragmentAddNoteBinding
import com.example.notesproject.viewmodel.NoteViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView : View

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view
       val categoryDao = noteViewModel.getCategoriesNames()
        val prios = listOf<Int>(1,2,3,4)
        val spinnerPrio : Spinner = binding.newNotePriority
        val arrayAdapterPrio = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prios)
        arrayAdapterPrio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrio.adapter = arrayAdapterPrio
        val spinner : Spinner = binding.newNoteCategorySpinner
        noteViewModel.getCategoriesNames().observe(viewLifecycleOwner, { categories ->
            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter

        })
    }


    private  fun saveNote(view:View){
        val noteTitle = binding.newNoteTitleEditText.text.toString().trim()
        val noteContent = binding.newNoteContentEditText.text.toString().trim()
        val noteCategory = binding.newNoteCategorySpinner.selectedItemPosition
        val notePrio = binding.newNotePriority.selectedItemPosition
        if(noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteContent,noteCategory,notePrio)

            noteViewModel.addNote(note)

            Toast.makeText(addNoteView.context,"Saved",Toast.LENGTH_SHORT).show()
            findNavController()?.popBackStack(R.id.homeFragment,false)
        } else {
            Toast.makeText(addNoteView.context, " Enter note's title",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_note_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveNewNoteMenu->{
                saveNote(addNoteView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }
}