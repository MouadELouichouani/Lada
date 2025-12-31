package project.ma.lada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import project.ma.lada.ui.theme.LadaTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import project.ma.lada.presentation.ui.HomeScreen
import project.ma.lada.presentation.viewmodel.GreetingViewModel
import project.ma.lada.domain.usecase.GetGreetingUseCase
import project.ma.lada.LadaApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LadaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val appContainer = (application as LadaApplication).container
                    val viewModel: GreetingViewModel = viewModel(
                        factory = ViewModelFactory(appContainer.getGreetingUseCase)
                    )
                    val state by viewModel.state.collectAsState()
                    
                    HomeScreen(
                        state = state,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class ViewModelFactory(
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GreetingViewModel::class.java)) {
            return GreetingViewModel(getGreetingUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}