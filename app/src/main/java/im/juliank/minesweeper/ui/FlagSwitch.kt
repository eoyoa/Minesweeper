package im.juliank.minesweeper.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlagSwitch(modifier: Modifier, isFlagMode: Boolean, onClick: (Boolean) -> Unit) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = isFlagMode, onCheckedChange = onClick)
        Spacer(modifier = modifier.size(10.dp))
        Text("Place flags")
    }
}