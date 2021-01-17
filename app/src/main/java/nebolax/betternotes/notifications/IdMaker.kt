package nebolax.betternotes.notifications

import android.content.SharedPreferences
import android.util.Log

class IdMaker private constructor(private val prefs: SharedPreferences) {
    private var current = prefs.getInt("AlexNotifiesIdentificator", Int.MIN_VALUE)

    fun getNext(): Int {
        Log.i("AlexIdmaker", current.toString())
        current++
        prefs.edit().putInt("AlexNotifiesIdentificator", current).apply()
        return current
    }

    companion object {
        private var isCreated = false
        private lateinit var instance: IdMaker
        fun getInstance(prefs: SharedPreferences): IdMaker {
                if (!isCreated) {
                    instance = IdMaker(prefs)
                    isCreated = true
                }
            return instance
        }
    }
}