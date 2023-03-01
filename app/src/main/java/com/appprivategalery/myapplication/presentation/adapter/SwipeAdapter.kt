package com.appprivategalery.myapplication.presentation.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import asm.asmtunis.com.mhcolorpicker.dialog.MHColorsDialog
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.NoteItem
import com.appprivategalery.myapplication.databinding.EditNoteListItemBinding
import com.bumptech.glide.Glide
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter

class SwipeAdapter( private val context: Context) : RecyclerSwipeAdapter<SwipeAdapter.SimpleViewHolder>(){

    private lateinit var binding : EditNoteListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
      binding = EditNoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder()
    }
    
    private val callback = object : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)



  

    private var clickListener: ClickListener? = null

    override fun onBindViewHolder(viewHolder: SimpleViewHolder, position: Int) {
        viewHolder.setData(differ.currentList[position])
        mItemManger.bindView(viewHolder.itemView, position)
    }

    override fun getItemCount()=differ.currentList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    private fun colorDialog(editText: EditText, noteItem: NoteItem) {
        MHColorsDialog(context)
            .setColorListener { colors, _ ->
                    editText.setTextColor(colors)
                    noteItem.colorText = colors
                    differ.submitList(differ.currentList)
            }
            .show()
    }



   inner class SimpleViewHolder() : RecyclerView.ViewHolder(binding.root), OnClickListener {
       
        init {
            binding.addMedia.setOnClickListener(this)
            binding.mainLayout.setOnClickListener{
                clickListener!!.onItemClick(true, null, adapterPosition)
            }
            val swipeLayout =  itemView.findViewById<View>(R.id.swipe) as SwipeLayout
            swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

            // Drag From Left
             swipeLayout.addDrag(
                SwipeLayout.DragEdge.Left,
                 swipeLayout.findViewById(R.id.bottom_wrapper1)
            )


             swipeLayout.addDrag(SwipeLayout.DragEdge.Right,  swipeLayout.findViewById(R.id.bottom_wrapper));


            // Handling different events when swiping
             swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener {
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
             swipeLayout.surfaceView.setOnClickListener {
                 clickListener!!.onItemClick(true, null, adapterPosition)
            }


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
                colorDialog(binding.info,  differ.currentList[adapterPosition])
            }

            binding.info.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isNotEmpty() && s.subSequence(s.length -1, s.length).toString() == "\n"){
                        clickListener!!.onItemClick(true, null, adapterPosition)
                        val updateText = binding.info.text.toString().replace("\n", "")
                        binding.info.setText(updateText)
                    }

                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // текст будет изменен
                }

                override fun afterTextChanged(s: Editable) {
                    differ.currentList[adapterPosition].info = s.toString()
                }
            })
        }


       override fun onClick(v: View?) {
           if(v!=null){
               clickListener?.onItemClick(false, binding.media, adapterPosition)
           }
       }

       fun setData(item: NoteItem) {
           binding.info.setText(item.info)
           item.urlToMedia?.let {
               if(it.isNotEmpty()) {
                   binding.media.visibility = View.VISIBLE
                   Glide.with(context)
                       .load(it)
                       .into(binding.media)
               }
           }
           item.colorText?.let { binding.info.setTextColor(it) }

       }
   }


    interface ClickListener {
        fun onItemClick(addItem : Boolean, imageView: ImageView?, adapterPosition : Int)
    }



    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }


}