package zi.aris.feature_shared

import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun View.visibilityExtension(showViewElement: Boolean){
    visibility = if (showViewElement){
        View.VISIBLE
    }else
        View.GONE
}

fun EditText.textObserver(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}
