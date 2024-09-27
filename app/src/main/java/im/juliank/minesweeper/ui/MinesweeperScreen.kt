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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.floor

@Composable
fun MinesweeperScreen(modifier: Modifier, viewModel: MinesweeperViewModel = viewModel()) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Minesweeper")
        FlagCheckbox(
            modifier = Modifier.fillMaxWidth(),
            viewModel.isFlagMode,
            onClick = { newVal ->
                viewModel.isFlagMode = newVal
            }
        )
        MinesweeperBoard(viewModel.board, cellCallback = viewModel::onCellClick)
    }
}

@Composable
fun MinesweeperBoard(board: Array<Array<CellType>>, cellCallback: (CellCoord) -> Unit) {
    val rows = board.size
    val cols = board[0].size

    Canvas(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(cols.toFloat() / rows)
            .pointerInput(key1 = Unit) {
                detectTapGestures {offset ->
                    val x = floor((offset.x / size.width) * cols).toInt()
                    val y = floor((offset.y / size.height) * rows).toInt()
                    cellCallback(CellCoord(x, y))
                }
            }
            .border(1.dp, Color.Black)
    ) {
        val colWidth = size.width / cols
        for (i in 1 until cols) {
            drawLine(
                color = Color.Black,
                start = Offset(colWidth * i, 0f),
                end = Offset(colWidth * i, size.height)
            )
        }

        val rowHeight = size.height / rows
        for (i in 1 until rows) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, rowHeight * i),
                end = Offset(size.width, rowHeight * i)
            )
        }

        for (row in board.indices) {
            for (col in 0 until board[0].size) {
                if (board[row][col] != CellType.MINE) continue

                drawCircle(
                    color = Color.Black,
                    radius = colWidth / 2,
                    center = Offset(
                        (col.toFloat() / board[0].size) * size.width + colWidth / 2,
                        (row.toFloat() / board.size) * size.height + colWidth / 2
                    )
                )
            }
        }
    }
}