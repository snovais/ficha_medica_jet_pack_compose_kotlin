package com.fichamed.ui.splash

import android.app.Application
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fichamed.data.PrefsRepository
import com.fichamed.ui.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(onLogado: () -> Unit, onNaoLogado: () -> Unit) {
    val context = LocalContext.current
    val repo = remember { PrefsRepository(context) }

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scale"
    )
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1500)
        val logado = repo.isLogado.first()
        if (logado) onLogado() else onNaoLogado()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Primary),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + scaleIn(initialScale = 0.7f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(12.dp),
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.MedicalServices, null,
                            tint = Primary,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text("FichaMed", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold, letterSpacing = (-1).sp)
                Spacer(Modifier.height(8.dp))
                Text("Sistema de Fichas Médicas", color = Color.White.copy(alpha = 0.75f), fontSize = 15.sp)
                Spacer(Modifier.height(48.dp))
                CircularProgressIndicator(color = Color.White.copy(alpha = 0.5f), strokeWidth = 2.5.dp, modifier = Modifier.size(32.dp))
            }
        }
    }
}
