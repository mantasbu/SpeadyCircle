package com.kotlisoft.speedycircle.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kotlisoft.speedycircle.Constants.BEST_SCORE_KEY
import com.kotlisoft.speedycircle.Constants.BEST_SCORE_SHARED_PREFERENCES_NAME
import com.kotlisoft.speedycircle.R

@Composable
fun GameOverDialog(
    score: Int,
    onPlayClick: () -> Unit,
    onExitClick: () -> Unit
) {
    val sharedPreference = LocalContext.current.getSharedPreferences(
        BEST_SCORE_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
    )
    val bestScore = sharedPreference.getInt(BEST_SCORE_KEY, 0)
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.tap_circle),
                fontSize = 24.sp
            )
            Text(
                text = "${stringResource(id = R.string.best_score)}: $bestScore",
                fontSize = 20.sp
            )
            Text(
                text = "${stringResource(id = R.string.your_score)}: $score",
                fontSize = 20.sp
            )
            Button(onClick = onPlayClick, modifier = Modifier.width(96.dp)) {
                Text(
                    text = stringResource(R.string.play),
                    fontSize = 20.sp
                )
            }
            Button(onClick = onExitClick, modifier = Modifier.width(96.dp)) {
                Text(
                    text = stringResource(R.string.exit),
                    fontSize = 20.sp
                )
            }
        }
    }
}
