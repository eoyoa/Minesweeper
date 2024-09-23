package im.juliank.minesweeper.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MinesweeperScreen(modifier: Modifier, viewModel: MinesweeperViewModel = viewModel()) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Minesweeper")
        FlagCheckbox(modifier = Modifier.fillMaxWidth(), viewModel.isFlagMode, onClick = {newVal ->
            viewModel.isFlagMode = newVal
        })
        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1.0f)
                .pointerInput(key1 = Unit) {
                    detectTapGestures {
                        offset -> Log.d("TAG_CLICK", "Click: ${offset.x}, ${offset.y}")
                    }
                }
        ) {
            drawRect(Color.Black, size = this.size)
        }
    }
}