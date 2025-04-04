package com.example.academia.navigation


import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.academia.R
import kotlinx.coroutines.delay

@Composable
fun InicioScreen(navigateToLogin: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var alpha by remember { mutableStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 3500, easing = EaseInOutCubic)
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 1500, easing = EaseInOutCubic)
    )

    LaunchedEffect(Unit) {
        delay(300)
        scale = 2f  // Aumenta el tamaño al doble
    //    delay(2000)
        alpha = 0.1f  // Se desvanece
        delay(1000)
        navigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.fondoOscuro))
            .graphicsLayer(alpha = animatedAlpha),

    contentAlignment = Alignment.Center
    ) {
        Box(
//            modifier = Modifier
//                .size(120.dp)
//                .scale(animatedScale)
//                .shadow(12.dp, shape = RoundedCornerShape(24.dp))
//                .clip(RoundedCornerShape(24.dp))
//                .background(Color.White)
        )
        {
            Text(text = "Academia",
                color = colorResource(id = R.color.tex1),
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }
//        {
//            Image(
//                painter = painterResource(id = R.drawable.app_icon),
//                contentDescription = "Logo de la App",
//                modifier = Modifier.fillMaxSize()
//            )
//        }
    }
}