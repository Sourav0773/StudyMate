package com.example.studymate.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * A full-screen splash that draws a flat cartoon (books + thumbs up) using Canvas,
 * plus text and animated dots. No external assets required.
 *
 * Add this Activity or call CartoonSplashScreen from your nav graph.
 */

// Optional: Example activity to preview/run
class CartoonSplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    CartoonSplashScreen(onTimeout = {})
                }
            }
        }
    }
}

@Composable
fun CartoonSplashScreen(
    modifier: Modifier = Modifier,
    title: String = "Study Mate",
    subtitle: String = "Plan • Focus • Achieve",
    durationMillis: Int = 2400,
    onTimeout: () -> Unit
) {
    // pop-in scale + fade for the character block
    val scaleAnim = remember { Animatable(0.75f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // animate scale with slight overshoot (spring-like)
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 650, easing = EaseOutBack)
        )
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        // keep splash visible for remainder, then callback
        val elapsed = 650 + 500
        delay((durationMillis - elapsed).coerceAtLeast(0).toLong())
        onTimeout()
    }

    // animated dot indicator
    val infinite = rememberInfiniteTransition()
    val dotShift = infinite.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(450, easing = LinearEasing), RepeatMode.Reverse)
    )

    // Gradient background
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF2E3A87), Color(0xFF2F5EBB))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Character + books + thumb container
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            ) {
                // circular backdrop behind character
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.06f))
                )

                // Canvas drawing the cartoon character and books
                CartoonCharacterAndBooks(
                    modifier = Modifier
                        .size(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(38.dp))

            // Dots
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val dotSize = 10.dp
                Dot(diameter = dotSize, offsetY = dotShift.value.dp, active = true)
                Spacer(modifier = Modifier.width(12.dp))
                Dot(diameter = dotSize, offsetY = (dotShift.value * 0.5f).dp, active = false)
                Spacer(modifier = Modifier.width(12.dp))
                Dot(diameter = dotSize, offsetY = (dotShift.value * 0.2f).dp, active = false)
            }
        }
    }
}

@Composable
private fun Dot(diameter: Dp, offsetY: Dp, active: Boolean) {
    val color = if (active) Color.White else Color.White.copy(alpha = 0.45f)
    Box(
        modifier = Modifier
            .size(diameter)
            .offset(y = offsetY)
            .clip(CircleShape)
            .background(color)
    )
}

/**
 * Draws a simple flat-style cartoon with:
 * - circular head + hair + glasses
 * - torso simplified
 * - stack of three books in front-left
 * - right hand giving thumbs up
 *
 * Everything drawn with primitives so it scales cleanly.
 */
@Composable
fun CartoonCharacterAndBooks(modifier: Modifier = Modifier) {
    // size conversions (canvas uses px)
    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // center positions
        val cx = w / 2f
        val cy = h / 2f

        // Colors
        val skin = Color(0xFFFFCBA4)
        val hair = Color(0xFF3A2E2E)
        val shirt = Color(0xFF2E68B2)
        val glassStroke = Color(0xFF2A2A2A)
        val book1 = Color(0xFFEB7A3F) // orange
        val book2 = Color(0xFFD33F4A) // red
        val book3 = Color(0xFF4DA3E0) // blue

        // Head (circle)
        val headRadius = minOf(w, h) * 0.22f
        val headCenter = Offset(cx, cy - h * 0.08f)
        drawCircle(color = skin, radius = headRadius, center = headCenter)

        // Hair (simple oval top)
        val hairOvalRect = Rect(
            offset = Offset(headCenter.x - headRadius, headCenter.y - headRadius * 1.05f),
            size = Size(headRadius * 2f, headRadius * 1.1f)
        )
        drawOval(color = hair, topLeft = hairOvalRect.topLeft, size = hairOvalRect.size)

        // Eyes (small dots)
        val eyeOffsetX = headRadius * 0.5f
        val eyeOffsetY = headRadius * 0.12f
        val eyeRadius = headRadius * 0.1f
        drawCircle(color = Color.Black, radius = eyeRadius, center = Offset(headCenter.x - eyeOffsetX, headCenter.y - eyeOffsetY))
        drawCircle(color = Color.Black, radius = eyeRadius, center = Offset(headCenter.x + eyeOffsetX, headCenter.y - eyeOffsetY))

        // Glasses (two rings + bridge)
        val glassRadius = headRadius * 0.35f
        val glassStrokeWidth = size.minDimension * 0.02f
        drawCircle(
            color = Color.Transparent,
            radius = glassRadius,
            center = Offset(headCenter.x - eyeOffsetX, headCenter.y - eyeOffsetY),
            style = Stroke(width = glassStrokeWidth, cap = StrokeCap.Round),
            brush = SolidColor(glassStroke)
        )
        drawCircle(
            color = Color.Transparent,
            radius = glassRadius,
            center = Offset(headCenter.x + eyeOffsetX, headCenter.y - eyeOffsetY),
            style = Stroke(width = glassStrokeWidth, cap = StrokeCap.Round),
            brush = SolidColor(glassStroke)
        )
        // bridge
        drawLine(
            color = glassStroke,
            strokeWidth = glassStrokeWidth,
            start = Offset(headCenter.x - eyeOffsetX + glassRadius * 0.6f, headCenter.y - eyeOffsetY),
            end = Offset(headCenter.x + eyeOffsetX - glassRadius * 0.6f, headCenter.y - eyeOffsetY)
        )

        // Smile (arc)
        val smileWidth = headRadius * 0.9f
        val smileTop = headCenter.y + headRadius * 0.2f
        drawArc(
            color = Color(0xFF6B3B2B),
            startAngle = 200f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(headCenter.x - smileWidth / 2f, smileTop - headRadius * 0.05f),
            size = Size(smileWidth, headRadius * 0.5f),
            style = Stroke(width = size.minDimension * 0.012f, cap = StrokeCap.Round)
        )

        // Torso (rounded rectangle under head)
        val torsoTop = headCenter.y + headRadius * 0.9f
        val torsoRect = Rect(
            offset = Offset(cx - w * 0.28f, torsoTop),
            size = Size(w * 0.56f, h * 0.34f)
        )
        drawRoundRect(color = shirt, topLeft = torsoRect.topLeft, size = torsoRect.size, cornerRadius = CornerRadius(24f, 24f))

        // Books stack (front-left)
        val bookW = w * 0.22f
        val bookH = h * 0.07f
        val booksBaseX = cx - w * 0.18f
        val booksBaseY = torsoTop + h * 0.06f

        // bottom book (blue)
        drawRoundRect(
            color = book3,
            topLeft = Offset(booksBaseX, booksBaseY + bookH * 1.6f),
            size = Size(bookW, bookH),
            cornerRadius = CornerRadius(8f, 8f)
        )
        // middle book (red)
        drawRoundRect(
            color = book2,
            topLeft = Offset(booksBaseX, booksBaseY + bookH * 0.8f),
            size = Size(bookW, bookH),
            cornerRadius = CornerRadius(8f, 8f)
        )
        // top book (yellow/offwhite)
        drawRoundRect(
            color = book1,
            topLeft = Offset(booksBaseX, booksBaseY),
            size = Size(bookW, bookH),
            cornerRadius = CornerRadius(8f, 8f)
        )
        // book spines lines
        val spineStroke = Stroke(width = size.minDimension * 0.006f)
        drawLine(
            color = Color.White.copy(alpha = 0.23f),
            start = Offset(booksBaseX + 6f, booksBaseY),
            end = Offset(booksBaseX + 6f, booksBaseY + bookH * 2.6f),
            strokeWidth = spineStroke.width
        )

        // Right arm + thumb up (simplified)
        val armLeft = cx + w * 0.04f
        val armTop = torsoTop + h * 0.02f
        val handW = w * 0.18f
        val handH = h * 0.18f

        // forearm rectangle (skin tone)
        drawRoundRect(
            color = skin,
            topLeft = Offset(armLeft, armTop + h * 0.04f),
            size = Size(handW * 0.5f, handH * 0.55f),
            cornerRadius = CornerRadius(14f, 14f)
        )

        // palm
        drawRoundRect(
            color = skin,
            topLeft = Offset(armLeft + handW * 0.44f, armTop + h * 0.02f),
            size = Size(handW * 0.4f, handH * 0.5f),
            cornerRadius = CornerRadius(12f, 12f)
        )

        // thumb (up)
        val thumbPath = Path().apply {
            val tLeft = armLeft + handW * 0.72f
            val tTop = armTop - h * 0.02f
            moveTo(tLeft, tTop + handH * 0.12f)
            quadTo(tLeft + 8f, tTop - 6f, tLeft + 20f, tTop + handH * 0.15f)
            quadTo(tLeft + 24f, tTop + handH * 0.35f, tLeft + 12f, tTop + handH * 0.45f)
            quadTo(tLeft, tTop + handH * 0.36f, tLeft, tTop + handH * 0.12f)
            close()
        }
        drawPath(path = thumbPath, color = skin)
    }
}

// small easing to make pop-in feel nice
private val EaseOutBack = CubicBezierEasing(0.175f, 0.885f, 0.32f, 1.275f)

@Preview(showBackground = true)
@Composable
fun CartoonSplashPreview() {
    MaterialTheme {
        CartoonSplashScreen(onTimeout = {})
    }
}
