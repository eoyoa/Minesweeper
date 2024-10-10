package im.juliank.minesweeper.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.floor

@Composable
fun MinesweeperScreen(modifier: Modifier, viewModel: MinesweeperViewModel = viewModel()) {
    val playing = viewModel.currentState == GameState.IN_PROGRESS
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Minesweeper")
        if (!playing) Text("You ${viewModel.currentState.name.lowercase()}!")
        FlagCheckbox(
            modifier = Modifier.fillMaxWidth(),
            viewModel.isFlagMode,
            onClick = { newVal ->
                viewModel.isFlagMode = newVal
            }
        )
        MinesweeperBoard(cellCallback = viewModel::onCellClick)
    }
}

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
        for (i in 1 until cols) {
            drawLine(
                color = Color.Black,
                start = Offset(cellSize * i, 0f),
                end = Offset(cellSize * i, size.height)
            )
        }
        for (i in 1 until rows) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, cellSize * i),
                end = Offset(size.width, cellSize * i)
            )
        }

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
                        drawRoundRect(
                            color = Color.Green,
                            topLeft = location,
                            size = Size(cellSize, cellSize),
                            cornerRadius = CornerRadius(cellSize / 2, cellSize / 2)
                        )
                    }
                    continue
                }

                when (viewModel.numbers[row][col]) {
                    -1 -> {
                        drawCircle(
                            color = Color.Black,
                            radius = cellSize / 2,
                            center = Offset(location.x + cellSize / 2, location.y + cellSize / 2)
                        )
                    }
                    0 -> {
                        drawRect(
                            color = Color.Blue,
                            topLeft = location,
                            size = Size(cellSize, cellSize)
                        )
                    }
                    else -> {
                        drawRect(
                            color = Color.Red,
                            topLeft = location,
                            size = Size(cellSize, cellSize)
                        )
                        drawText(
                            textLayoutResult,
                            topLeft = Offset(location.x + (cellSize - textLayoutResult.size.width) / 2, location.y + (cellSize - textLayoutResult.size.height) / 2)
                        )
                    }
                }
            }
        }

        if (remainingUnchecked == viewModel.mines) {
            viewModel.currentState = GameState.WON
        }
    }
}