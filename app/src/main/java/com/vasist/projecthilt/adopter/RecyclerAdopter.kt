package com.vasist.projecthilt.adopter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vasist.projecthilt.R
import com.vasist.projecthilt.UserInfo
import com.vasist.projecthilt.model.Result

class RecyclerAdopter(private val context: Context, private var userData: MutableList<Result>):
    RecyclerView.Adapter<RecyclerAdopter.RecyclerViewHolder>(){

    class RecyclerViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.idUserName)
        var phone: TextView = view.findViewById(R.id.idUserPhone)
        var email: TextView = view.findViewById(R.id.idUserEmail)
        var profile: ImageView = view.findViewById(R.id.ProfileImageView)
        var process: ProgressBar =view.findViewById(R.id.idProgress_bar)
        var street: TextView =view.findViewById(R.id.Street)
        var city: TextView =view.findViewById(R.id.city)
        var state: TextView =view.findViewById(R.id.State)
        var country: TextView =view.findViewById(R.id.Country)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return RecyclerViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {


        val list = userData[position]
        holder.name.text=list.name.first
        holder.phone.text=list.phone
        holder.email.text=list.email
        holder.street.text=list.location.street.name
        holder.city.text=list.location.city
        holder.state.text=list.location.state
        holder.country.text=list.location.country
        holder.profile.load(list.picture.large){
            listener { _, _ ->
                holder.process.isGone = true
            }
        }
        holder.itemView.setOnClickListener{
            val i = Intent(context,UserInfo::class.java)
            i.putExtra("position",userData[position])
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return userData.size
    }
    fun updateList(list: MutableList<Result>){
        userData = list
        notifyDataSetChanged()
    }
}