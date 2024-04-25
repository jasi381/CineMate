package com.jasmeet.cinemate.presentation.appComponents.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.libreBaskerville


@Composable
fun LoadingButton(
    onClick: () -> Unit,
    loading: Boolean,
    shape: Shape = customShapeAllCorners,
    backgroundColor: Color = Color(0xffE50914),
    textColor: Color = Color.White,
    text: String ?= null,
    fontFamily: FontFamily = libreBaskerville,
    modifier: Modifier,
    textSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
    content: @Composable () -> Unit ={}
) {
    Button(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        enabled = !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f),

            )
    ) {

        AnimatedVisibility(
            visible = loading,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = textColor.copy(alpha = 0.7f),
                strokeWidth = 2.5f.dp,
                strokeCap = StrokeCap.Round,
            )
        }
        AnimatedVisibility(
            visible = !loading,
        ) {
            if (text != null) {
                Text(
                    text = text,
                    fontFamily = fontFamily,
                    fontSize = textSize,
                    modifier = Modifier.padding(vertical = 3.5.dp),
                    fontWeight = fontWeight
                )
            }else{
                content()
            }
        }

    }
}
