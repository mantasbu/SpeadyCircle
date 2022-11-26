package com.kotlisoft.speedycircle

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kotlisoft.speedycircle.Constants.BEST_SCORE_KEY
import com.kotlisoft.speedycircle.Constants.RADIUS
import kotlin.math.sqrt

class MainViewModel : ViewModel() {

    fun isCircleClicked(
        clickedX: Float,
        clickedY: Float,
        circleX: Int,
        circleY: Int
    ): Boolean {
        val x = clickedX - circleX
        val y = clickedY - circleY
        return sqrt(x * x + y * y) <= RADIUS
    }

    fun updateScoreIfBest(
        score: Int,
        context: Context
    ) {
        val sharedPreference = context.getSharedPreferences(
            Constants.BEST_SCORE_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
        )
        val bestScore = sharedPreference.getInt(BEST_SCORE_KEY, 0)
        if (score > bestScore) {
            sharedPreference.edit().apply {
                putInt(BEST_SCORE_KEY, score)
                commit()
            }
        }
    }
}