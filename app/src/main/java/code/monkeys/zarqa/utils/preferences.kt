package code.monkeys.zarqa.utils

import android.content.Context
import android.content.SharedPreferences

class preferences(context: Context) {
    private val TAG_STATUS = "status"
    private val TAG_LEVEL = "level"
    private val TAG_APP = "app"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(TAG_APP, Context.MODE_PRIVATE)

//    Getter & Setter
    var prefStatus: Boolean
        get() = sharedPreferences.getBoolean(TAG_STATUS, false)
        set(value) =  sharedPreferences.edit().putBoolean(TAG_STATUS, value).apply()

    var prefLevel: String?
        get() = sharedPreferences.getString(TAG_LEVEL, "")
        set(value) =  sharedPreferences.edit().putString(TAG_LEVEL, value).apply()

//    Clear
    fun prefClear() {
        sharedPreferences.edit().remove(TAG_STATUS).apply()
        sharedPreferences.edit().remove(TAG_LEVEL).apply()
    }

}