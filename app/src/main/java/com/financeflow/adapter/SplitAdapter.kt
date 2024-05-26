package com.financeflow.adapter

import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.financeflow.Interface.OnCheckBoxclick
import com.financeflow.R
import com.financeflow.model.GoalDatum
import com.financeflow.model.UserData
import com.financeflow.model.UserDatum
import com.financeflow.screen.UpdateGoalActivity
import kotlinx.android.synthetic.main.item_budgetadapter.view.budget
import kotlinx.android.synthetic.main.item_goaladapter.view.budget1
import kotlinx.android.synthetic.main.item_splitadapter.view.checkbox
import kotlinx.android.synthetic.main.item_splitadapter.view.username
import java.io.Serializable


class SplitAdapter(private val c: Context,var onCheckBoxclick: OnCheckBoxclick) :
    RecyclerView.Adapter<SplitAdapter.BeatsViewHolder>() {
    private var userlist: List<UserData>? = null
    private var userId: Int = 0
    private var promolink : String = "test";
    private  var userId1: MutableList<String> = mutableListOf()

    fun setList(userlist1: List<UserData>,) {
        this.userlist = userlist1.reversed()
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeatsViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_splitadapter, parent, false)

        return BeatsViewHolder(v)

    }


    override fun getItemCount(): Int {
        if (userlist != null) {
            return userlist!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: BeatsViewHolder, position: Int) {



        holder.mName.text = userlist?.get(position)?.fullname.toString()

        Log.d("CHECK",userlist?.get(position)!!.isChecked.toString())

       if(userId1?.contains(userlist?.get(position)!!.id))
           {
               holder.mName1.isChecked = true
           }
        else{
           holder.mName1.isChecked = false

       }


        holder.mName1.setOnClickListener {

            if(userId1?.contains(userlist?.get(position)!!.id) == true){
                userId1.remove(userlist?.get(position)!!.id)

                onCheckBoxclick.onCheckClick(position,userlist!!.get(position).id.toString(),false)

            }
            else{
                userId1.add(userlist?.get(position)!!.id)
                onCheckBoxclick.onCheckClick(position,userlist!!.get(position).id.toString(),true)

            }
           /* if(!userlist!![position].isChecked){
                userId1.add(userlist!!.get(position).id.toString())
                userlist!![position].isChecked = true
                onCheckBoxclick.onCheckClick(position,userlist!!.get(position).id.toString(),true)

                Log.d("CHECK","call false")

            }
            if(userlist!![position].isChecked){
                userlist!![position].isChecked = false
                onCheckBoxclick.onCheckClick(position,userlist!!.get(position).id.toString(),false)
                Log.d("CHECK","call true")

            }*/
        }

       /* holder.itemView.setOnClickListener {

            val intent = Intent(c, UpdateGoalActivity::class.java)
            intent.putExtra("value", userlist?.get(position) as Serializable)
            intent.putExtra("id", userlist?.get(position)?.id)

            c.startActivity(intent)

                  }*/


    }

    inner class BeatsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var mName: TextView
        var mName1: CheckBox

        /*var mDate: TextView
         var mDur: TextView
        var avrimg: CircleImageView
       var mlastime: TextView*/


        init {
           /* mName = v.txt_title1
            avrimg = v.avatar
            mlastime = v.txt_time*/

             mName = v.username
            mName1 = v.checkbox


            /*mDate = v.item_date
            mDur = v.item_duration
            share1 = v.share
            play1 = v.play*/

            //  v.setOnClickListener(this)
        }

        /*  override fun onClick(v: View?) {
              TODO("Not yet implemented")
          }*/

    }

}