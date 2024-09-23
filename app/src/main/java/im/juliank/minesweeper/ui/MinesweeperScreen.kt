package im.juliank.minesweeper.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MinesweeperScreen(modifier: Modifier, viewModel: MinesweeperViewModel = viewModel()) {
    Column(modifier = modifier) {
        Text("Minesweeper")
        Text("Flagging: ${viewModel.isFlagMode}")
        FlagCheckbox(modifier = Modifier.fillMaxWidth(), viewModel.isFlagMode, onClick = {newVal ->
            viewModel.isFlagMode = newVal
        })
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(Color.Black, size = this.size)
        }
    }
}

@Composable
fun FlagCheckbox(modifier: Modifier, isFlagMode: Boolean, onClick: (Boolean) -> Unit) {
    Row(modifier = modifier) {
        Checkbox(checked = isFlagMode, onCheckedChange = onClick)
        Text("Place flags")
    }
}