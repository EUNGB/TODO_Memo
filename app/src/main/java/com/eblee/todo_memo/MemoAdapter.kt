package com.eblee.todo_memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.eblee.todo_memo.entity.MemoEntity

class MemoAdapter(
    val context: Context,
    var list: List<MemoEntity>,
    var listener: OnDeleteListener
) : RecyclerView.Adapter<MemoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memo: TextView = itemView.findViewById(R.id.tv_memo)
        val root: ConstraintLayout = itemView.findViewById(R.id.cl_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let {
            holder.memo.text = it.memo
            holder.root.setOnLongClickListener(View.OnLongClickListener {
                listener.onDelete(list[position])
                return@OnLongClickListener true
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}