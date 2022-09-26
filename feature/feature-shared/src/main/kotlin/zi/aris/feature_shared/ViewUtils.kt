package zi.aris.feature_shared

import android.view.View

fun View.visibilityExtension(showViewElement: Boolean){
    visibility = if (showViewElement){
        View.VISIBLE
    }else
        View.GONE
}
