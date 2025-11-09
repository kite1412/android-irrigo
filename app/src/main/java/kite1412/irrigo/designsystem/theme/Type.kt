package kite1412.irrigo.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kite1412.irrigo.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(
        resId = R.font.poppins_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.poppins_italic,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.poppins_bold_italic,
        style = FontStyle.Italic,
        weight = FontWeight.Bold
    )
)

val Quicksand = FontFamily(
    Font(R.font.quicksand_bold),
    Font(
        resId = R.font.quicksand_bold,
        weight = FontWeight.Bold
    )
)

private const val LINE_HEIGHT_MULTIPLIER = 1.4f

val Typography = Typography(
    bodyMedium = with(14) {
        TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = this.sp,
            lineHeight = (this * LINE_HEIGHT_MULTIPLIER).sp,
            letterSpacing = 0.4.sp
        )
    },
    bodySmall = with(12) {
        TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = this.sp,
            lineHeight = (this * LINE_HEIGHT_MULTIPLIER).sp,
            letterSpacing = 0.3.sp
        )
    },
    bodyLarge = with(16) {
        TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = this.sp,
            lineHeight = (this * LINE_HEIGHT_MULTIPLIER).sp,
            letterSpacing = 0.5.sp
        )
    },
    titleSmall = with(20) {
        TextStyle(
            fontFamily = Quicksand,
            fontWeight = FontWeight.Bold,
            fontSize = this.sp,
            lineHeight = (this * LINE_HEIGHT_MULTIPLIER).sp,
            letterSpacing = 0.7.sp
        )
    }
)

val Typography.bodyExtraSmall: TextStyle
    get() = with(10) {
        TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = this.sp,
            lineHeight = (this * LINE_HEIGHT_MULTIPLIER).sp,
            letterSpacing = 0.3.sp
        )
    }