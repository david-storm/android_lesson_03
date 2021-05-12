package com.onix.internship.survay.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.R
import com.onix.internship.survay.database.user.User

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var data = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val login: TextView = itemView.findViewById(R.id.login)
        val name: TextView = itemView.findViewById(R.id.name)
        val role: TextView = itemView.findViewById(R.id.role)
        val roleCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxRole)

        fun bind(item: User) {
            login.text = item.getLogin()
            name.text = item.getFirstName().plus(" ").plus(item.getSecondName())
            role.setText(
                when (item.getRole()) {
                    0 -> R.string.role_admin
                    1 -> R.string.role_manager
                    2 -> R.string.role_user
                    else -> R.string.role_other
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.user_item_view, parent, false)

                return ViewHolder(view)
            }
        }

    }

}
