package zi.aris.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userNavChooserUsecase: UserNavigationChooserUsecase) : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val navigation: MutableStateFlow<SplashScreenContract.UserNavOptions> =
        MutableStateFlow(SplashScreenContract.UserNavOptions.Idle)

    private val navChooser =
        flow {
            emit(userNavChooserUsecase.invoke())
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SplashScreenContract.UserNavOptions.Idle
            )

    val state: StateFlow<SplashScreenContract.SplashScreenState> =
        combine(loading, navigation) { loading, navigation ->
            SplashScreenContract.SplashScreenState(
                loading,
                navigation
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = SplashScreenContract.SplashScreenState()
            )

    private fun navigateUser() {
        navigation.update { navChooser.value }
    }

    fun consumeEvent(event: SplashScreenContract.SplashScreenEvent) {
        when (event) {
            is SplashScreenContract.SplashScreenEvent.UserLaunchedApp -> {
                navigateUser()
            }
        }
    }

}
