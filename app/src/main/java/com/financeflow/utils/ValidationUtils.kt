package  com.financeflow.util

import android.content.Context
import android.util.Patterns
import java.lang.Double.parseDouble
import java.util.regex.Pattern


class ValidationUtils(private val mContext: Context) {

    fun isValidConfirmPasswrod(confirmPassword: String, password: String): Boolean {
        return confirmPassword == password
    }

    fun isValidMobile(mobile: String?): Boolean {
        val p = Pattern.compile("^[6789]\\d{9,9}$")
        return if (mobile == null) {
            false
        } else {
            val m = p.matcher(mobile)
            m.matches()
        }
    }

    fun isValidPassword(password: String?): Boolean {
        val p =
            Pattern.compile("((?!\\s)\\A)(\\s|(?<!\\s)\\S){8,20}\\Z")
        return if (password == null) {
            false
        } else {
            val m = p.matcher(password)
            m.matches()
        }
    }

    fun isValidEmail(email: String?): Boolean {
        return if (email == "" || email == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun isValidCompEmail(email: String?): Boolean {
        return if (email == "" || email == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun isValidLastName(lastName: String?): Boolean {
        val p = Pattern.compile("^[a-zA-Z]{3,20}$")
        return if (lastName == null) {
            false
        } else {
            val m = p.matcher(lastName)
            m.matches()
        }
    }

    fun isValidFirstName(firstname: String?): Boolean {
        val p = Pattern.compile("^[a-zA-Z]{3,20}$")
        return if (firstname == null) {
            false
        } else {
            val m = p.matcher(firstname)
            m.matches()
        }
    }

    fun isValidAge(age: String?): Boolean {
        return !(age == null || age == "")
    }

    fun selectedLocation(s: String?): Boolean {
        return !(s == null || s == "")
    }

    fun isValidName(firstname: String?): Boolean {
        val p = Pattern.compile("^[a-zA-Z ]{3,20}$")
        return if (firstname == null) {
            false
        } else {
            val m = p.matcher(firstname)
            m.matches()
        }
    }

    fun isValidAddress(address: String?): Boolean {
        return !(address == null || address == "")
    }


    fun isNumber(myname: String?): Boolean {
        var numeric = true
        try {
            val numm = parseDouble(myname)
        } catch (e: NumberFormatException) {
            numeric = false
        }

        return numeric

    }

}