package com.appprivategalery.myapplication.presentation.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import asm.asmtunis.com.mhcolorpicker.dialog.MHColorsDialog
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.model.NoteItem
import com.bumptech.glide.Glide
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.robertlevonyan.components.picker.*

class SwipeAdapter( private val noteList: ArrayDeque<NoteItem>, private val context: Context) : RecyclerSwipeAdapter<SwipeAdapter.SimpleViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.edit_note_list_item, parent, false)

        return SimpleViewHolder(view)
    }

    private var clickListener: ClickListener? = null

    override fun onBindViewHolder(viewHolder: SimpleViewHolder, position: Int) {
            val item = noteList[position]
            viewHolder.info.setText(item.info)
            item.urlToMedia?.let {
                if(it.isNotEmpty()) {
                    Log.e("viewHolder", it)
                    viewHolder.media.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(it)
                        .into(viewHolder.media)
                }
            }
            item.colorText?.let { viewHolder.info.setTextColor(it) }



        viewHolder.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

        // Drag From Left
        viewHolder.swipeLayout.addDrag(
            SwipeLayout.DragEdge.Left,
            viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1)
        )


        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener {
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
        viewHolder.swipeLayout.surfaceView.setOnClickListener {
            addNoteToList(position)
        }

       // viewHolder.addMedia.setOnClickListener(this)

        viewHolder.underlined.setOnClickListener {
            val normalText: String = viewHolder.info.text.toString()
            val underlineText = SpannableString(normalText)
            underlineText.setSpan(UnderlineSpan(), 0, normalText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            viewHolder.info.setText(underlineText)
        }

        viewHolder.bold.setOnClickListener {
            viewHolder.info.setTypeface(null, Typeface.BOLD)
        }

        viewHolder.color.setOnClickListener {
            colorDialog(viewHolder.info, noteList[position])
            println(noteList[position].colorText)
        }

        viewHolder.info.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty() && s.subSequence(s.length -1, s.length).toString() == "\n"){
                    addNoteToList(position)
                    val updateText = viewHolder.info.text.toString().replace("\n", "")
                    viewHolder.info.setText(updateText)
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // текст будет изменен
            }

            override fun afterTextChanged(s: Editable) {
                noteList[position].info  = s.toString()

            }
        })




//        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                noteList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, noteList.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(view.getContext(), "Deleted " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    private fun addNoteToList(position: Int){
        if(noteList.size-1==position){
            noteList.add(NoteItem(position,"", R.color.white, isBold = false, underline = false, urlToMedia = ""))
            notifyItemChanged(position)

        }
        for (note in noteList){
            Log.e("notes", note.info.toString())
        }
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






   inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnClickListener {
        var media : ImageView
        var swipeLayout: SwipeLayout
        var info: EditText
        var color: ImageButton
        var addMedia: ImageButton
        var bold : ImageButton
        var underlined : ImageButton

        init {
            media = itemView.findViewById<View>(R.id.media) as ImageView
            swipeLayout = itemView.findViewById<View>(R.id.swipe) as SwipeLayout
            info = itemView.findViewById<View>(R.id.info) as EditText
            color = itemView.findViewById<View>(R.id.color) as ImageButton
            addMedia = itemView.findViewById<View>(R.id.addMedia) as ImageButton
            bold  = itemView.findViewById(R.id.bold) as ImageButton
            underlined = itemView.findViewById(R.id.underline)

            addMedia.setOnClickListener(this)
        }


       override fun onClick(v: View?) {
           if(v!=null){
               clickListener?.onItemClick(v, media, adapterPosition)
           }
       }
    }

    interface ClickListener {
        fun onItemClick(v: View, imageView: ImageView, adapterPosition : Int)
    }



    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }


}