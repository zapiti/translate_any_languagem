package br.com.zapiti.translateexpression.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.zapiti.translateexpression.R
import br.com.zapiti.translateexpression.utils.Language
import kotlinx.android.synthetic.main.adapter_card_simple.view.*


class SimpleFilterAdapter(
        val mDataset: ArrayList<Language>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var click: ((type: Language) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SimpleViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.adapter_card_simple, parent, false)
        )
    }

    override fun getItemCount() = mDataset.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is SimpleViewHolder) {
            initNoteViewHolder(holder, position)
        }

    }

    private fun initNoteViewHolder(holder: SimpleViewHolder, position: Int) {
        val type = mDataset[position]
        holder.itemView.card_title_item?.text = ("#${type.name}")
        holder.itemView.setOnClickListener {
            if (click != null) {
                click!!(type)
            }
        }
    }

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    fun onClickItem(click: (type: Language) -> Unit) {
        this.click = click
    }

}