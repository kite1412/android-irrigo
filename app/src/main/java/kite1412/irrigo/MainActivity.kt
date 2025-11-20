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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kite1412.irrigo.designsystem.theme.IrrigoTheme
import kite1412.irrigo.ui.compositionlocal.LocalAppBarUpdater
import kite1412.irrigo.ui.compositionlocal.LocalScaffoldBarsController
import kite1412.irrigo.ui.compositionlocal.LocalSnackbarHostState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: IrrigoViewModel = hiltViewModel()

            IrrigoTheme { innerPadding ->
                SplashScreen {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CompositionLocalProvider(
                            LocalAppBarUpdater provides viewModel.appBarUpdater,
                            LocalSnackbarHostState provides viewModel.snackbarHostState,
                            LocalScaffoldBarsController provides viewModel.scaffoldBarsController
                        ) {
                            IrrigoNavHost(
                                showAppBar = viewModel.showAppBar,
                                showNavBar = viewModel.showNavBar,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(
                                        vertical = 24.dp,
                                        horizontal = 16.dp
                                    ),
                                appBarSubtitle = viewModel.appBarSubtitle
                            )
                        }
                        NavigationBarProtection(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                        SnackbarHost(
                            hostState = viewModel.snackbarHostState,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(innerPadding)
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(
                                        horizontal = 24.dp,
                                        vertical = 16.dp
                                    )
                            ) {
                                Text(
                                    text = it.visuals.message,
                                    color = Color.Black
                                )
                            }
                        }
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