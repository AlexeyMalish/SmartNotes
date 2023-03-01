package com.appprivategalery.myapplication.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appprivategalery.myapplication.MainActivity
import com.appprivategalery.myapplication.MainActivity.Companion.permissionManager
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.data.model.NoteItem
import com.appprivategalery.myapplication.data.model.NoteList
import com.appprivategalery.myapplication.databinding.FragmentNoteInfoBinding
import com.appprivategalery.myapplication.presentation.adapter.SwipeAdapter
import com.appprivategalery.myapplication.presentation.viewmodel.note.NotesViewModel
import com.appprivategalery.myapplication.utils.permission.Permission
import com.bumptech.glide.Glide
import com.daimajia.swipe.util.Attributes
import com.google.android.material.snackbar.Snackbar
import com.robertlevonyan.components.picker.*
import java.text.FieldPosition


class NoteInfoFragment : Fragment() {
    private lateinit var fragmentNoteInfoBinding: FragmentNoteInfoBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mNote: Note


    private var noteList = ArrayDeque<NoteItem>()

    private var notUpdate = false

    private lateinit var mAdapter: SwipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        fragmentNoteInfoBinding = FragmentNoteInfoBinding.bind(view)
        val args: NoteInfoFragmentArgs by navArgs()
        mNote = args.selectedNote


        mNote.noteItems.noteItems?.let {
            for (note in it) {
                noteList.add(note)
            }
        }
        if (noteList.isEmpty())
            noteList.add(NoteItem(-1, null, null, null, null, null))


        notesViewModel = (activity as MainActivity).notesViewModel

        fragmentNoteInfoBinding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Creating Adapter object
        mAdapter = SwipeAdapter(requireContext())
        mAdapter.differ.submitList(noteList)
        mAdapter.mode = Attributes.Mode.Single



        mAdapter.setOnItemClickListener(object : SwipeAdapter.ClickListener {
            override fun onItemClick(
                addItem: Boolean,
                imageView: ImageView?,
                adapterPosition: Int
            ) {
                if (!addItem) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    try {
//                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//                        intent.addCategory("android.intent.category.DEFAULT")
//                        intent.data = Uri.parse(String.format("package:%s", requireActivity().packageName))
//                        requireActivity().startActivityForResult(intent, 101)
//                    } catch (e: Exception) {
//                        val intent = Intent()
//                        intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
//                        requireActivity().startActivityForResult(intent, 101)
//                    }
//                    permissionManager
//                        .request(Permission.Camera)
//                        .rationale("We want permission for Storage (Read+Write), Location (Fine+Coarse) and Camera")
//                        .checkDetailedPermission { result ->
//                            if (result.all { it.value }) {
//                                success("YES! Now I have full access :D")
//                            } else {
//                                showErrorDialog(result)
//                            }
//                        }
                    // } else {
                    permissionManager
                        .request(Permission.Storage, Permission.Camera)
                        .rationale("We want permission for Storage (Read+Write), Location (Fine+Coarse) and Camera")
                        .checkDetailedPermission { result ->
                            if (result.all { it.value }) {
                                success("YES! Now I have full access :D")
                            } else {
                                showErrorDialog(result)
                            }
                        }
                    // }

                    notUpdate = true
                    pickerDialog {
                        setTitle("Choice")
                        setTitleTextBold(true)
                        setTitleTextSize(22f)
                        setItems(
                            setOf(
                                ItemModel(ItemType.Camera),
                                // ItemModel(ItemType.Video),
                                ItemModel(ItemType.ImageGallery(MimeType.Image.Png)),
//                            ItemModel(ItemType.VideoGallery()),
//                            ItemModel(ItemType.AudioGallery(MimeType.Audio.Mp3)),
//                            ItemModel(ItemType.Files()),
                            )
                        )
                        setListType(PickerDialog.ListType.TYPE_GRID)
                    }.setPickerCloseListener { type, uris ->
                        Toast.makeText(requireContext(), uris.toString(), Toast.LENGTH_LONG).show()
                        notUpdate = false
//        val ivPreview = findViewById<ImageView>(R.id.ivPreview)
                        noteList[adapterPosition].urlToMedia = uris.first().toString()
                        imageView!!.visibility = View.VISIBLE
                        fragmentNoteInfoBinding.myRecyclerView.adapter?.notifyItemChanged(
                            adapterPosition
                        )

//        when (type) {
//          ItemType.Camera -> Glide.with(requireContext())
//              .load(uris.first())
//              .into(imageView) //ivPreview.load(uris.first())
//            is ItemType.ImageGallery ->  Glide.with(requireContext())
//                .load(uris.first())
//                .into(imageView) //ivPreview.load(uris.first())
//
//
////          is ItemType.Files -> println(uris.toTypedArray().contentToString())
//            else -> {}
//        }
                    }.show()
                }

                else {
                    if (mAdapter.itemCount-1 == adapterPosition) {
                        noteList.add(
                            NoteItem(
                                adapterPosition+1,
                                "",
                                R.color.white,
                                isBold = false,
                                underline = false,
                                urlToMedia = ""
                            )
                        )
                        mAdapter.differ.submitList(noteList)
                    }
                }
            }


        })

        fragmentNoteInfoBinding.myRecyclerView.adapter = mAdapter


        /* Scroll Listeners */
        fragmentNoteInfoBinding.myRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("RecyclerView", "onScrollStateChanged")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })



    }

    private fun showErrorDialog(result: Map<Permission, Boolean>) {
        val message = result.entries.fold("") { message, entry ->
            message + "${getErrorMessageFor(entry.key)}: ${entry.value}\n"
        }
        Log.i("TAG", message)
        AlertDialog.Builder(requireContext())
            .setTitle("Missing permissions")
            .setMessage(message)
            .show()
    }

    private fun getErrorMessageFor(permission: Permission) = when (permission) {
        Permission.Camera -> "Camera permission: "
        Permission.Storage -> "Storage permission"
        else -> "Unknown permission"
    }

    private fun success(message: String) {
        Snackbar.make(fragmentNoteInfoBinding.content, message, Snackbar.LENGTH_SHORT)
            .withColor(Color.parseColor("#09AF00"))
            .show()
    }

    private fun error(message: String) {
        Snackbar.make(fragmentNoteInfoBinding.content, message, Snackbar.LENGTH_SHORT)
            .withColor(Color.parseColor("#B00020"))
            .show()
    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

    override fun onPause() {
        notesViewModel.updateNote(
            Note(
                mNote.id,
                NoteList(noteList)
            )
        )
            super.onPause()
            Log.e("ddd", "ddd")
    }


    override fun onDestroyView() {
            notesViewModel.updateNote(
                Note(
                    mNote.id,
                    NoteList(noteList)
                )
            )
            super.onDestroyView()
    }


}