package com.appprivategalery.myapplication.presentation.adapter

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import asm.asmtunis.com.mhcolorpicker.dialog.MHColorsDialog
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.model.NoteItem
import com.appprivategalery.myapplication.databinding.EditNoteListItemBinding
import com.bumptech.glide.Glide
import com.daimajia.swipe.SwipeLayout

class Adapter(private val context : Context) : RecyclerView.Adapter<Adapter.SimpleViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }
        
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binding = EditNoteListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder(binding)
    }

   

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.bind(event)
    }
    
    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class SimpleViewHolder(val binding: EditNoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(event: NoteItem) {

            binding.info.setText(event.info)
            if(event.urlToMedia?.isNotEmpty() == true){
                binding.media.visibility = View.VISIBLE
                Glide.with(context)
                    .load(event.urlToMedia)
                    .into(binding.media)
            }
            event.colorText?.let { 
                binding.info.setTextColor(it)
            }

            binding.swipe.showMode = SwipeLayout.ShowMode.PullOut

            // Drag From Left
            binding.swipe.addDrag(
                SwipeLayout.DragEdge.Left,
                binding.swipe.findViewById(R.id.bottom_wrapper1)
            )


            binding.swipe.addDrag(
                SwipeLayout.DragEdge.Right, binding.swipe.findViewById(
                    R.id.bottom_wrapper));


            // Handling different events when swiping
            binding.swipe.addSwipeListener(object : SwipeLayout.SwipeListener {
                override fun onClose(layout: SwipeLayout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {
                    //you are swiping.
                }

                override fun onStartOpen(layout: SwipeLayout) {}
                override fun onOpen(layout: SwipeLayout) {
                    //when the BottomView totally show.
                }

                override fun onStartClose(layout: SwipeLayout) {}
                override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {
                    //when user's hand released.
                }
            })
            binding.swipe.surfaceView.setOnClickListener {
                addNoteToList(position)
            }

            // binding.addMedia.setOnClickListener(this)

            binding.underline.setOnClickListener {
                val normalText: String = binding.info.text.toString()
                val underlineText = SpannableString(normalText)
                underlineText.setSpan(UnderlineSpan(), 0, normalText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.info.setText(underlineText)
            }

            binding.bold.setOnClickListener {
                binding.info.setTypeface(null, Typeface.BOLD)
            }

            binding.color.setOnClickListener {
                colorDialog(binding.info, event)
            }

            binding.info.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // текст только что изменили
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // текст будет изменен
                }

                override fun afterTextChanged(s: Editable) {
                    //noteList[position].info  = s.toString()
                }
            })

        }
    }

    private var onItemClickListener: ((Event) -> Unit)? = null

    fun setOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }

    private fun colorDialog(editText: EditText, noteItem: NoteItem) {
        MHColorsDialog(context)
            .setColorListener { colors, _ ->
                editText.setTextColor(colors)
                noteItem.colorText = colors
            }
            .show()

        var i : ArrayList<String>?

    }

    private fun addNoteToList(position: Int){
//        if(noteList.size-1==position){
//            differ.submitList()
//            noteList.add(NoteItem(position,"", R.color.white, isBold = false, underline = false, urlToMedia = ""))
//            notifyItemChanged(position)
//
//        }
//        for (note in noteList){
//            Log.e("notes", note.info.toString())
//        }
    }

   

}