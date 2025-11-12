package io.github.cadnunsdimir.android.duceiapp.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.duceiapp.service.translate

@Composable
fun SendButton(onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier.size(width = 200.dp, height = 50.dp),
        enabled
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = translate("component_send_button_icon_label"),
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(translate("component_send_button_label"))
    }
}