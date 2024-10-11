package im.juliank.minesweeper.util

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText

fun DrawScope.drawFlag(
    cellSize: Float,
    location: Offset
) {
    drawRoundRect(
        color = Color.Green,
        topLeft = location,
        size = Size(cellSize, cellSize),
        cornerRadius = CornerRadius(cellSize / 2, cellSize / 2)
    )
}

fun DrawScope.drawUnsafe(
    cellSize: Float,
    location: Offset,
    textLayoutResult: TextLayoutResult
) {
    drawRect(
        color = Color.Red,
        topLeft = location,
        size = Size(cellSize, cellSize)
    )
    drawText(
        textLayoutResult,
        topLeft = Offset(
            location.x + (cellSize - textLayoutResult.size.width) / 2,
            location.y + (cellSize - textLayoutResult.size.height) / 2
        )
    )
}

fun DrawScope.drawSafe(
    cellSize: Float,
    location: Offset
) {
    drawRect(
        color = Color.Blue,
        topLeft = location,
        size = Size(cellSize, cellSize)
    )
}

fun DrawScope.drawMine(
    cellSize: Float,
    location: Offset
) {
    drawCircle(
        color = Color.Black,
        radius = cellSize / 2,
        center = Offset(location.x + cellSize / 2, location.y + cellSize / 2)
    )
}

fun DrawScope.drawLines(
    cols: Int,
    cellSize: Float,
    rows: Int
) {
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
}