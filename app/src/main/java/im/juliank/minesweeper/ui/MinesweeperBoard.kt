
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import im.juliank.minesweeper.ui.CellCoord
import im.juliank.minesweeper.ui.CellState
import im.juliank.minesweeper.ui.GameState
import im.juliank.minesweeper.ui.MinesweeperViewModel
import im.juliank.minesweeper.util.drawFlag
import im.juliank.minesweeper.util.drawLines
import im.juliank.minesweeper.util.drawMine
import im.juliank.minesweeper.util.drawSafe
import im.juliank.minesweeper.util.drawUnsafe
import kotlin.math.floor

@Composable
fun MinesweeperBoard(
    cellCallback: (CellCoord) -> Unit,
    viewModel: MinesweeperViewModel = viewModel()
) {
    val board = viewModel.board
    val rows = board.size
    val cols = board[0].size

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(cols.toFloat() / rows)
            .pointerInput(key1 = Unit) {
                detectTapGestures { offset ->
                    val x = floor((offset.x / size.width) * cols).toInt()
                    val y = floor((offset.y / size.height) * rows).toInt()
                    cellCallback(CellCoord(x, y))
                }
            }
            .border(1.dp, Color.Black)
    ) {
        val cellSize = size.minDimension / cols
        drawLines(cols, cellSize, rows)

        var remainingUnchecked = 0
        for (row in board.indices) {
            for (col in 0 until board[0].size) {
                val location = Offset(
                    (col.toFloat() / board[0].size) * size.width,
                    (row.toFloat() / board.size) * size.height
                )
                val textLayoutResult = textMeasurer.measure(
                    "${viewModel.numbers[row][col]}",
                    style = TextStyle(fontSize = cellSize.toSp(), color = Color.White)
                )

                if (board[row][col] != CellState.OPENED) {
                    remainingUnchecked++
                    if (board[row][col] == CellState.FLAGGED) {
                        drawFlag(cellSize, location)
                    }
                    continue
                }

                when (viewModel.numbers[row][col]) {
                    -1 -> {
                        drawMine(cellSize, location)
                    }
                    0 -> {
                        drawSafe(cellSize, location)
                    }
                    else -> {
                        drawUnsafe(cellSize, location, textLayoutResult)
                    }
                }
            }
        }

        if (remainingUnchecked == viewModel.mines) {
            viewModel.currentState = GameState.WON
        }
    }
}