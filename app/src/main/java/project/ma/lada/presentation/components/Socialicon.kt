package project.ma.lada.presentation.components
import project.ma.lada.R

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SocialButton(
    iconRes: Int,
    contentDescription: String? = null,
    backgroundColor: Color = Color.White,
    iconTint: Color? = null,
    buttonSize: Dp = 50.dp,
    iconSize: Dp = 24.dp,
    cornerRadius: Dp = 12.dp,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(buttonSize),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            colorFilter = iconTint?.let { ColorFilter.tint(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SocialButtonPreview() {
    SocialButton(
        iconRes = R.drawable.google,
        backgroundColor = Color.White,
        iconTint = null
    )
}