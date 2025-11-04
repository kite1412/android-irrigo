package kite1412.irrigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kite1412.irrigo.designsystem.theme.IrrigoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IrrigoTheme { innerPadding ->
                SplashScreen {
                    IrrigoNavHost(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(
                                vertical = 24.dp,
                                horizontal = 16.dp
                            )
                    )
                }
            }
        }
    }
}