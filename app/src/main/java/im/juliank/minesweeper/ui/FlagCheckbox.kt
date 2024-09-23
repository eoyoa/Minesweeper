package im.juliank.minesweeper.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FlagCheckbox(modifier: Modifier, isFlagMode: Boolean, onClick: (Boolean) -> Unit) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isFlagMode, onCheckedChange = onClick)
        Text("Place flags")
    }
}