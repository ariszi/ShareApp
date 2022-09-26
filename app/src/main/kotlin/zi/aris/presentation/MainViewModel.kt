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

    private val navigation: MutableStateFlow<MainScreenContract.UserNavOptions> =
        MutableStateFlow(MainScreenContract.UserNavOptions.Idle)

    private val navChooser =
        flow {
            emit(userNavChooserUsecase.invoke())
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = MainScreenContract.UserNavOptions.Idle
            )

    val state: StateFlow<MainScreenContract.MainScreenState> =
        combine(loading, navigation) { loading, navigation ->
            MainScreenContract.MainScreenState(
                loading,
                navigation
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MainScreenContract.MainScreenState()
            )

    private fun navigateUser() {
        navigation.update { navChooser.value }
    }

    fun consumeEvent(event: MainScreenContract.MainScreenEvent) {
        when (event) {
            is MainScreenContract.MainScreenEvent.UserLaunchedApp -> {
                navigateUser()
            }
        }
    }

}
