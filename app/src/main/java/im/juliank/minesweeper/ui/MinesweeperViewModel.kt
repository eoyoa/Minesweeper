package im.juliank.minesweeper.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MinesweeperViewModel: ViewModel() {
    var isFlagMode by mutableStateOf(false)
}