package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreditsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Guardianship Credits",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )
        }

        // Scrollable Credits
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Big Eco Logo represent
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "Eco Logo",
                tint = Color(0xFF81C784),
                modifier = Modifier
                    .size(80.dp)
                    .shadow(4.dp, RoundedCornerShape(100))
            )

            Text(
                text = "CALL OF NATURE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            Text(
                text = "\"Every small action can help protect our planet, nurture habitats, and rescue species for generations to come.\"",
                fontSize = 15.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFFD54F),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            )

            // Contributor text block
            CreditsCategory(
                title = "Gamedev & Coding Design",
                names = listOf("Google AI Studio Builder Agent", "Powered by Gemini 3.5 Flash Model", "Antigravity Engineering")
            )

            CreditsCategory(
                title = "Syllabus & Conservation Concept",
                names = listOf("International Union for Conservation of Nature", "Evergreen Island Research Department", "Eco-Guardians Council")
            )

            CreditsCategory(
                title = "Creative Audio Placeholders",
                names = listOf("Peaceful Breeze Synthesizer", "Splashing Water Wave Generator", "Forest Wildlife Orchestration")
            )

            Text(
                text = "Developed entirely in modern Jetpack Compose for Android.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun CreditsCategory(title: String, names: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title.uppercase(),
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = Color(0xFF81C784),
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        names.forEach { name ->
            Text(
                text = name,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}
