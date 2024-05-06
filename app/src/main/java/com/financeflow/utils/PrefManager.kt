package  com.financeflow.util

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "Ekeeda_New"
    val MYTOKEN = "token"
    val MYEMAIL = "email"
    val MYPHONE = "phone"
    val MYNAME= "name"
    val MYLNAME= "lname"
    val MYPROFILE= "myprofile"
    val CONFIG= "config"
    val LOCALNOTIFICATIONCOUNT= "notificationcount"
    val DEVICETOKEN = "devicetoken"
    val MYINT= "interest"
    val CAPINT= "capint"
    val USERID= "userid"


    val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)


    var userid: String?
        get() = pref.getString(USERID, "0")
        set(value) = pref.edit().putString(USERID, value).apply()

    var config: String?
        get() = pref.getString(CONFIG, "")
        set(value) = pref.edit().putString(CONFIG, value).apply()


    var profile: String?
        get() = pref.getString(MYPROFILE, "")
        set(value) = pref.edit().putString(MYPROFILE, value).apply()


    var usertoken: String?
        get() = pref.getString(MYTOKEN, "")
        set(value) = pref.edit().putString(MYTOKEN, value).apply()

    var devicetoken: String?
        get() = pref.getString(DEVICETOKEN, "")
        set(value) = pref.edit().putString(DEVICETOKEN, value).apply()


    var name: String?
        get() = pref.getString(MYNAME, "")
        set(value) = pref.edit().putString(MYNAME, value).apply()

    var lname: String?
        get() = pref.getString(MYLNAME, "")
        set(value) = pref.edit().putString(MYLNAME, value).apply()


    var email: String?
        get() = pref.getString(MYEMAIL, "")
        set(value) = pref.edit().putString(MYEMAIL, value).apply()

    var phonenumber: String?
        get() = pref.getString(MYPHONE, "")
        set(value) = pref.edit().putString(MYPHONE, value).apply()


    var interest: String?
        get() = pref.getString(MYINT, "")
        set(value) = pref.edit().putString(MYINT, value).apply()

    var capinterest: Boolean
        get() = pref.getBoolean(CAPINT, false)
        set(value) = pref.edit().putBoolean(CAPINT, value).apply()
}