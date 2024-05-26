package com.financeflow.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.financeflow.Interface.OnPayclick
import com.financeflow.R
import com.financeflow.model.GoalDatum
import com.financeflow.model.PDatum
import com.financeflow.screen.UpdateGoalActivity
import kotlinx.android.synthetic.main.item_budgetadapter.view.budget
import kotlinx.android.synthetic.main.item_goaladapter.view.budget1
import kotlinx.android.synthetic.main.item_requestadapter.view.btn_pay
import kotlinx.android.synthetic.main.item_requestadapter.view.titleamount
import kotlinx.android.synthetic.main.item_requestadapter.view.titleby
import kotlinx.android.synthetic.main.item_requestadapter.view.titledate
import kotlinx.android.synthetic.main.item_requestadapter.view.titlename
import java.io.Serializable


class RequestAdapter(private val c: Context,var onPayclick: OnPayclick) :
    RecyclerView.Adapter<RequestAdapter.BeatsViewHolder>() {
    private var userlist: List<PDatum>? = null
    private var userId: Int = 0
    private var promolink : String = "test";

    fun setList(userlist1: List<PDatum>,) {
        this.userlist = userlist1.reversed()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeatsViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_requestadapter, parent, false)

        return BeatsViewHolder(v)

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

        holder.mNametitle.text = userlist?.get(position)?.title.toString()
        holder.mNameAmount.text = userlist?.get(position)?.amount.toString()
        holder.mNameDate.text = userlist?.get(position)?.createdAt.toString().split("T")[0]
        holder.mNamBy.text = "Requested by "+userlist?.get(position)?.userId!!.fullName.toString()

        holder.mPAy.setOnClickListener {

           onPayclick.onPayClick(userlist?.get(position)?.id.toString())

                  }


    }

    inner class BeatsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var mNametitle: TextView
        var mNameAmount: TextView
        var mNameDate: TextView
        var mNamBy: TextView
        var mPAy: Button

        /*var mDate: TextView
         var mDur: TextView
        var avrimg: CircleImageView
       var mlastime: TextView*/


        init {
            mNametitle = v.titlename
            mNameAmount = v.titleamount
            mNameDate = v.titledate
            mNamBy = v.titleby
            mPAy = v.btn_pay

           /*  mName = v.budget
            mName1 = v.budget1*/


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