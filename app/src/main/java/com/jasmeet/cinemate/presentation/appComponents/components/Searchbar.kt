package com.jasmeet.cinemate.presentation.appComponents.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.theme.libreBaskerville

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    labelValue: String? = null,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Search,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    readyOnly: Boolean = false,
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    fontFamily: FontFamily = libreBaskerville,
    fontSize: TextUnit = 16.sp,
    enabled: Boolean = true,
    onSearch: () -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        shape = shape,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.7f)
        ),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onSearch.invoke() }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_unselected),
                        contentDescription = null
                    )

                }
            }

        },

        placeholder = {
            if (labelValue != null)
                Text(
                    text = labelValue,
                    color = labelColor,
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                    )
                )
        },

        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        maxLines = 1,
        singleLine = true,
        readOnly = readyOnly,
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onSurface,
        ),
        enabled = enabled
    )

}