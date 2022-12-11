package com.appprivategalery.myapplication.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.appprivategalery.myapplication.MainActivity
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.data.model.NoteItem
import com.appprivategalery.myapplication.data.model.NoteList
import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.databinding.FragmentHomeBinding
import com.appprivategalery.myapplication.presentation.adapter.NotesAdapter
import com.appprivategalery.myapplication.presentation.adapter.VoiceNotesAdapter
import com.appprivategalery.myapplication.presentation.viewmodel.note.NotesViewModel
import com.appprivategalery.myapplication.presentation.viewmodel.voiceNote.VoiceNotesViewModel
import com.appprivategalery.myapplication.utils.VoiceRecorder

import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    private lateinit var voiceNotesViewModel: VoiceNotesViewModel
    private lateinit var voiceNotesAdapter: VoiceNotesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        notesViewModel = (activity as MainActivity).notesViewModel
        notesAdapter = (activity as MainActivity).notesAdapter

        setNoteListItemTouchHelper()

        notesAdapter.setOnItemClickListener {
            Bundle().apply {
                if (it.id == null)
                    notesViewModel.saveNote(Note(null, NoteList(kotlin.collections.ArrayList<NoteItem>())))
                else {
                    putSerializable("selected_note", it)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_noteInfoFragment,
                        this
                    )
                }
            }
        }

        initNotesRecyclerView()


        notesViewModel.savedNotes().observe(viewLifecycleOwner) {
            val editedList: ArrayList<Note> = ArrayList(it)
            editedList.add(Note(null, NoteList(kotlin.collections.ArrayList<NoteItem>())))
            notesAdapter.differ.submitList(editedList)
        }

        //nagrania

        voiceNotesViewModel = (activity as MainActivity).voiceNotesViewModel
        voiceNotesAdapter = (activity as MainActivity).voiceNotesAdapter

        setVoiceNoteListItemTouchHelper()

        voiceNotesAdapter.setOnItemClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(it.memoryLocation)
            mp.prepare()
            mp.start()
        }

        var voiceRecorder = VoiceRecorder(this.requireContext())

        fragmentHomeBinding.hfIbRecordStart.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    activity as MainActivity,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    activity as MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(activity as MainActivity, permissions, 0)
            } else {
                fragmentHomeBinding.hfIbRecordStart.visibility = View.INVISIBLE
                fragmentHomeBinding.hfIbRecordStop.visibility = View.VISIBLE
                voiceRecorder.start()
            }
        }

        fragmentHomeBinding.hfIbRecordStop.setOnClickListener {
            fragmentHomeBinding.hfIbRecordStart.visibility = View.VISIBLE
            fragmentHomeBinding.hfIbRecordStop.visibility = View.INVISIBLE
            voiceRecorder.stopRecording()
            voiceNotesViewModel.saveVoiceNote(
                VoiceNote(
                    null,
                    voiceRecorder.fileName,
                    "voiceRecorder."
                )
            )
            //Log.e("===>", voiceRecorder.output.toString())
        }

        fragmentHomeBinding.hfTvWelcome.text = getTimeFromWelcoming()

        initVoiceNotesRecyclerView()

        voiceNotesViewModel.savedVoiceNotes().observe(viewLifecycleOwner) {
            voiceNotesAdapter.differ.submitList(it)
        }

    }

    private fun initNotesRecyclerView() {
        fragmentHomeBinding.hfRvNotes.apply {
            adapter = notesAdapter
            layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)

        }
    }

    private fun initVoiceNotesRecyclerView() {
        fragmentHomeBinding.hfRvVoiceNotes.apply {
            adapter = voiceNotesAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }
    }


    private fun getTimeFromWelcoming(): String {
        val dt = Date()
        val hours: Int = dt.getHours()
        when (hours) {
            in 1..11 -> {
                return requireContext().getString(R.string.good_moring)
            }
            in 12..17 -> {
                return requireContext().getString(R.string.good_afternoon)
            }
            in 18..24, 0 -> {
                return requireContext().getString(R.string.good_evening)
            }
            else -> return "Hello"
        }
    }

    private fun setNoteListItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = notesAdapter.differ.currentList[viewHolder.adapterPosition]
                notesViewModel.deleteNote(note)
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                if (viewHolder.adapterPosition == notesAdapter.differ.currentList.size - 1) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

        }).attachToRecyclerView(fragmentHomeBinding.hfRvNotes)
    }


    private fun setVoiceNoteListItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val voiceNote = voiceNotesAdapter.differ.currentList[viewHolder.adapterPosition]
                voiceNotesViewModel.deleteVoiceNote(voiceNote)
            }
        }).attachToRecyclerView(fragmentHomeBinding.hfRvVoiceNotes)
    }
}

