package com.example.room_assigment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter (var items:ArrayList<User>,var context:Context):RecyclerView.Adapter<UserViewHolder>(){
    lateinit var mListener: UserItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.tvUser.text = items[position].name

        userViewHolder.btnDelete.setOnClickListener {
            mListener.onItemDeleteClicked(position)
        }

        userViewHolder.itemView.setOnLongClickListener {
            mListener.onItemLongClicked(position)
            true
        }
    }

    fun setListener(listener: UserItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<User>) {
        this.items = items
    }

    fun appendData(newUserAdded: User) {
        this.items.add(newUserAdded)
        notifyItemInserted(items.size - 1)
    }

}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvUser = view.tvUser
    var btnDelete = view.btnDelete
}
