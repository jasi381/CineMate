package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jasmeet.cinemate.presentation.theme.libreBaskerville

@Composable
fun RowScope.CustomTab(
    text: String,
    isSelected: Boolean,
    index: Int,
    onTabClick: (Int) -> Unit,

    ) {

    val interactionSource = remember { MutableInteractionSource() }

    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Color.White
        } else {
            Color.White.copy(alpha = 0.5f)
        },
        animationSpec = tween(500, 0, LinearEasing),
        label = "Text Color",
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onTabClick.invoke(index) },
        contentAlignment = Alignment.Center
    ) {

        androidx.compose.animation.AnimatedVisibility(
            visible = isSelected,
            enter = slideInHorizontally(
                initialOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)

            )

        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 6.dp, horizontal = 5.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xff636363)),
                shape = RoundedCornerShape(8.dp),
            ) {}
        }

        TextComponent(
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            textColor = tabTextColor,
            modifier = Modifier.padding(vertical = 4.dp),

            )
    }
}


@Composable
fun RowScope.CustomTab2(
    text: String,
    isSelected: Boolean,
    index: Int,
    onTabClick: (Int) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }

    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Color.White
        } else {
            Color.White.copy(alpha = 0.5f)
        },
        animationSpec = tween(500, 0, LinearEasing),
        label = "Text Color",
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onTabClick.invoke(index) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        TextComponent(
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            textColor = tabTextColor,
            modifier = Modifier.padding(vertical = 4.dp),
            fontFamily = libreBaskerville

            )

        androidx.compose.animation.AnimatedVisibility(
            visible = isSelected,
            modifier = Modifier,
            enter = slideInHorizontally(
                initialOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)

            )

        ) {
            Card(
                modifier = Modifier
                    .width(text.length.dp * 6f)
                    .height(2.dp)
                    ,
                colors = CardDefaults.cardColors(containerColor = Color(0xffE50914)),
                shape = RoundedCornerShape(8.dp),
            ) {}
        }


    }
}
