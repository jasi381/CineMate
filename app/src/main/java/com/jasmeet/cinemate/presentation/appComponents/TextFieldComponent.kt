package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.cinemate.presentation.theme.libreBaskerville

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    labelValue: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    readyOnly : Boolean = false,
    labelColor : Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    leadingIcon : Painter,
    leadingIconTintColor : Color = MaterialTheme.colorScheme.onSurface,
    shape: Shape = RoundedCornerShape(8.dp),
    fontFamily: FontFamily = libreBaskerville
) {
    OutlinedTextField(
        value = value,
        onValueChange ={
            onValueChange.invoke(it)
        },
        placeholder = {
            Text(text = labelValue, color = labelColor)
        },
        shape = shape,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor =MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
        ),
        leadingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                tint = leadingIconTintColor
            )
        },
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
            autoCorrect = false,
        ),
        maxLines = 1,
        singleLine = true,
        readOnly = readyOnly,
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
        )
    )

}