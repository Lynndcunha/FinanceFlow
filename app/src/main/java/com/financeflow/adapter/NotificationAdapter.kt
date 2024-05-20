package com.financeflow.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.financeflow.R
import com.financeflow.model.BudgetData
import com.financeflow.model.ExpenseDatum
import com.financeflow.model.NotiData
import com.financeflow.screen.UpdateExpenseActivity
import com.financeflow.screen.UpdateGoalActivity
import kotlinx.android.synthetic.main.item_budgetadapter.view.budget
import kotlinx.android.synthetic.main.item_goaladapter.view.budget1
import java.io.Serializable


class NotificationAdapter(private val c: Context,) :
    RecyclerView.Adapter<NotificationAdapter.BeatsViewHolder>() {
    private var userlist: List<NotiData>? = null
    private var userId: Int = 0
    private var promolink : String = "test";


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeatsViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_notificationadapter, parent, false)

        return BeatsViewHolder(v)

    }

    fun setList(userlist1: List<NotiData>) {
        this.userlist = userlist1.reversed()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        if (userlist != null) {
            return userlist!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: BeatsViewHolder, position: Int) {

       /* holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(c, ChatRoomActivity::class.java)
            intent.putExtra("id", userlist!![position].id)
            intent.putExtra("type", userlist!![position].communityType)
            intent.putExtra("img", userlist!![position].image)
            intent.putExtra("userid", userId.toString())

            c.startActivity(intent)

            *//* val intent = Intent(c, CompetationDetails_Activity::class.java)
             intent.putExtra("cbanner", userlist!![position].banner)
             intent.putExtra("ctitle", userlist!![position].title)
             intent.putExtra("cdesc", userlist!![position].description)
             intent.putExtra("cprice", userlist!![position].prizes)
             intent.putExtra("crules", userlist!![position].rules)
             intent.putExtra("cparticpate", userlist!![position].howToParticipate)
             intent.putExtra("cispaid", userlist!![position].isPaid)
             intent.putExtra("cisparticipate", userlist!![position].isParticipated)
             intent.putExtra("cuplodtype", userlist!![position].uploadType)
             intent.putExtra("camount", userlist!![position].amount)
             intent.putExtra("ccategory", userlist!![position].category)
             intent.putExtra("ccategoryID", userlist!![position].competitionId)

             c.startActivity(intent)*//*
        })*/

       /* Glide.with(c)
            .load(userlist!![position].image)
            .into(holder.avrimg)*/

//       Glide.with(c).load("https://celebrityschool.in/images/mobile_app/singing_competition_card_image.jpg").into(holder.courseimg)

     /*   holder.mName.text = userlist!!.get(position).title!!.trim()
        holder.mlastime.text = userlist!!.get(position).sentTime?.let {
            gettime(
                it
            )
        }*/

        holder.mName.text = userlist!!.get(position).title!!.trim()
        holder.mName1.text = userlist!!.get(position).body!!.trim()


    }

    inner class BeatsViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        var mName: TextView
        var mName1: TextView

        /*var mDur: TextView
       var avrimg: CircleImageView
      var mlastime: TextView*/


        init {
           mName = v.budget
            mName1 = v.budget1

            /*  avrimg = v.avatar
             mlastime = v.txt_time*/


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