package im.juliank.minesweeper.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

enum class CellType {
    MINE,
    NONE
}

class CellCoord(val x: Int, val y: Int)

class MinesweeperViewModel: ViewModel() {
    var isFlagMode by mutableStateOf(false)

    val rows = 16
    val cols = 9
    val mines = 3

    var board by mutableStateOf(
        Array(rows) { Array(cols) { CellType.NONE } }
    )
    init {
        for (i in 1..mines) {
            var placed = false
            val rng = Random.Default

            while (!placed) {
                val row = rng.nextInt(rows)
                val col = rng.nextInt(cols)

                if (board[row][col] == CellType.MINE) continue

                board[row][col] = CellType.MINE
                placed = true
            }
        }
    }

    fun onCellClick(coord: CellCoord) {
        val newBoard = board.copyOf()
        newBoard[coord.y][coord.x] = CellType.MINE

        board = newBoard
    }
}