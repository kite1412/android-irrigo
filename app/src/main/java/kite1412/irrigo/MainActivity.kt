package kite1412.irrigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kite1412.irrigo.designsystem.theme.IrrigoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IrrigoTheme { innerPadding ->
                SplashScreen {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        IrrigoNavHost(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(
                                    vertical = 24.dp,
                                    horizontal = 16.dp
                                )
                        )
                        NavigationBarProtection(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationBarProtection(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(
                    WindowInsets
                        .navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .background(Color.Black.copy(alpha = 0.3f))
        )
    }
}