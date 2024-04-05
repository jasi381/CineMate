package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.cinemate.presentation.theme.libreBaskerville


@Composable
fun OrDivider(
    modifier: Modifier
) {

    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = "Or",
            color = Color.White,
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }

}