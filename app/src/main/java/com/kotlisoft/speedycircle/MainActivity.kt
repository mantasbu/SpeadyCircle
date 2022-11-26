package com.kotlisoft.speedycircle

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.kotlisoft.speedycircle.Constants.OFFSET_X
import com.kotlisoft.speedycircle.Constants.OFFSET_Y
import com.kotlisoft.speedycircle.Constants.TIME_LEFT
import com.kotlisoft.speedycircle.ui.Circle
import com.kotlisoft.speedycircle.ui.GameOverDialog
import com.kotlisoft.speedycircle.ui.theme.SpeedyCircleTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContent {
            val screenWidthInPx = with(LocalDensity.current) {
                LocalConfiguration.current.screenWidthDp.dp.roundToPx()
            }
            val screenHeightInPx = with(LocalDensity.current) {
                LocalConfiguration.current.screenHeightDp.dp.roundToPx()
            }

            val maxX = screenWidthInPx - OFFSET_X
            val maxY = screenHeightInPx - OFFSET_Y

            var centerX by remember {
                mutableStateOf(Random.nextInt(OFFSET_X, maxX))
            }
            var centerY by remember {
                mutableStateOf(Random.nextInt(OFFSET_Y, maxY))
            }

            var secondsLeft by remember { mutableStateOf(TIME_LEFT) }
            var gameOver by remember { mutableStateOf(true) }
            var score by remember { mutableStateOf(0) }

            val countDownTimer = object : CountDownTimer(TIME_LEFT * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsLeft--
                }
                override fun onFinish() {
                    gameOver = true
                }
            }

            SpeedyCircleTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { tapOffset ->
                                    val clicked = mainViewModel.isCircleClicked(
                                        clickedX = tapOffset.x,
                                        clickedY = tapOffset.y,
                                        circleX = centerX,
                                        circleY = centerY
                                    )
                                    if (clicked) {
                                        score++
                                        centerX = Random.nextInt(OFFSET_X, maxX)
                                        centerY = Random.nextInt(OFFSET_Y, maxY)
                                    }
                                }
                            )
                        }
                ) {
                    if (gameOver) {
                        mainViewModel.updateScoreIfBest(
                            score = score,
                            context = LocalContext.current
                        )
                        GameOverDialog(
                            score = score,
                            onPlayClick = {
                                gameOver = false
                                score = 0
                                secondsLeft = TIME_LEFT
                                countDownTimer.start()
                            },
                            onExitClick = {
                                finish()
                            }
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${stringResource(id = R.string.time)}: $secondsLeft",
                                color = Color.White,
                                fontSize = 24.sp
                            )
                            Text(
                                text = "${stringResource(id = R.string.score)}: $score",
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                        Circle(
                            centerX = centerX.toFloat(),
                            centerY = centerY.toFloat()
                        )
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        // Hide the action bar at the top
        actionBar?.hide()

        // Hide the status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}