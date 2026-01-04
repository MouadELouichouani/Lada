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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SocialButton(
    iconRes: Int,
    contentDescription: String? = null,
    backgroundColor: Color = Color.White,
    iconTint: Color? = null,
    size: Dp = 50.dp,
    cornerRadius: Dp = 12.dp,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(size),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(size / 2),
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