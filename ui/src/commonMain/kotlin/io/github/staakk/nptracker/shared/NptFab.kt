package io.github.staakk.nptracker.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.staakk.nptracker.Dimens

@Composable
fun NptFab(
    text: String,
    onClick: () -> Unit,
    icon: @Composable RowScope.() -> Unit = { Icon(Icons.Filled.Add, contentDescription = null) },
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(100),
    ) {
        Row(
            modifier = Modifier
                .padding(Dimens.standard),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Spacer(Modifier.width(Dimens.half))
            Text(text)
        }

    }
}