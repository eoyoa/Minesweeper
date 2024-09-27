package im.juliank.minesweeper.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

enum class GameState {
    WON,
    LOST,
    IN_PROGRESS
}

enum class CellState {
    OPENED,
    UNOPENED,
    FLAGGED
}

class CellCoord(val x: Int, val y: Int)

class MinesweeperViewModel: ViewModel() {
    var isFlagMode by mutableStateOf(false)

    var currentState by mutableStateOf(GameState.IN_PROGRESS)

    val rows = 16
    val cols = 9
    val mines = 3

    var board by mutableStateOf(
        Array(rows) { Array(cols) { CellState.UNOPENED } }
    )
    var numbers = Array(rows) { Array(cols) { 0 } }
    init {
        for (i in 1..mines) {
            var placed = false
            val rng = Random.Default

            while (!placed) {
                val row = rng.nextInt(rows)
                val col = rng.nextInt(cols)

                if (numbers[row][col] == -1) continue

                numbers[row][col] = -1
                fillAdjacentUnsafe(row, col)

                placed = true
            }
        }
    }

    fun initUnsafe(row: Int, col: Int) {
        if (isOutOfBounds(row, col)) return
        if (numbers[row][col] == -1) return

        numbers[row][col]++
    }
    fun fillAdjacentUnsafe(row: Int, col: Int) {
        initUnsafe(row - 1, col - 1)
        initUnsafe(row - 1, col)
        initUnsafe(row - 1, col + 1)

        initUnsafe(row, col - 1)
        initUnsafe(row, col + 1)

        initUnsafe(row + 1, col - 1)
        initUnsafe(row + 1, col)
        initUnsafe(row + 1, col + 1)
    }

    fun onCellClick(coord: CellCoord) {
        Log.d("CLICK", "CLICKED x: ${coord.x}, y: ${coord.y}")
        val newBoard = board.copyOf()

        if (numbers[coord.y][coord.x] == -1) {
            currentState = GameState.LOST
            board = Array(rows) { Array(cols) { CellState.OPENED } }
            return
        }

        reveal(newBoard, coord)

        board = newBoard
    }

    fun isOutOfBounds(row: Int, col: Int): Boolean {
        if (row < 0 || row >= numbers.size) {
            return true
        }
        if (col < 0 || col >= numbers[0].size) {
            return true
        }
        return false
    }

    private fun reveal(newBoard: Array<Array<CellState>>, coord: CellCoord) {
        Log.d("REVEAL", "REVEALING x: ${coord.x}, y: ${coord.y}")
        if (isOutOfBounds(coord.y, coord.x)) {
            return
        }

        val field = newBoard[coord.y][coord.x]
        if (field != CellState.UNOPENED) {
            return
        }

        val cell = numbers[coord.y][coord.x]
        newBoard[coord.y][coord.x] = CellState.OPENED
        if (cell != 0) {
            return
        }

        reveal(newBoard, CellCoord(coord.x - 1, coord.y))
        reveal(newBoard, CellCoord(coord.x + 1, coord.y))
        reveal(newBoard, CellCoord(coord.x, coord.y - 1))
        reveal(newBoard, CellCoord(coord.x, coord.y + 1))
    }
}