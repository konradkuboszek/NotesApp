package com.example.notesproject.framents

import android.app.AlertDialog
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainActivity
import com.example.notesproject.R
import com.example.notesproject.database.Note
import com.example.notesproject.databinding.FragmentEditNoteBinding
import com.example.notesproject.viewmodel.NoteViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {
    // TODO: Rename and change types of parameters

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

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
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitleEditText.setText(currentNote.noteTitle)
        binding.editNoteContentEditText.setText(currentNote.noteContent)
       // binding.editNotePriority.setSelection(currentNote.prio-1)
        val categoryDao = noteViewModel.getCategoriesNames()
        val prios = listOf<Int>(1,2,3,4)
        val spinner : Spinner = binding.editNoteCategorySpinner
        val spinnerPrio : Spinner = binding.editNotePriority
        val arrayAdapterPrio = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prios)
        arrayAdapterPrio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrio.adapter = arrayAdapterPrio
        spinnerPrio.setSelection(currentNote.prio)
        noteViewModel.getCategoriesNames().observe(viewLifecycleOwner, { categories ->
            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
            spinner.setSelection(currentNote.category)
        }

        )

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_note_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteNoteMenu -> {
                deleteNote()
                true
            }
            R.id.saveNoteMenu -> {
                saveNote()
                true
            }else -> false
        }


    }
    private fun saveNote(){
        val noteTitle = binding.editNoteTitleEditText.text.toString()
        val noteContent = binding.editNoteContentEditText.text.toString()
        val noteCategory = binding.editNoteCategorySpinner.selectedItemPosition
        val notePrio = binding.editNotePriority.selectedItemPosition
        if(noteTitle.isNotEmpty()){
            val note = Note(currentNote.id,noteTitle,noteContent,noteCategory,notePrio)
            noteViewModel.updateNote(note)
            view?.findNavController()?.popBackStack(R.id.homeFragment, false)

        }else{
            Toast.makeText(context,"Enter title", Toast.LENGTH_SHORT).show()
        }

    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are u sure?")
            setPositiveButton("Delete"){_,_->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }

}