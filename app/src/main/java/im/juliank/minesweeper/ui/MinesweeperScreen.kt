package im.juliank.minesweeper.ui

import MinesweeperBoard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MinesweeperScreen(modifier: Modifier, viewModel: MinesweeperViewModel = viewModel()) {
    val playing = viewModel.currentState == GameState.IN_PROGRESS
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Text("Minesweeper")
        FlagSwitch(
            modifier = Modifier,
            viewModel.isFlagMode,
            onClick = { newVal ->
                if (viewModel.currentState != GameState.LOST) viewModel.isFlagMode = newVal
            }
        )
        MinesweeperBoard(cellCallback = viewModel::onCellClick)
        Text(if (playing) "" else "You ${viewModel.currentState.name.lowercase()}!")
    }
}