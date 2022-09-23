package zi.aris.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Preview
    @Composable
    fun MessageCard(name: String = "hello") {
        Row {
            Text(modifier = Modifier.padding(all = 8.dp), text = "Fucking hell")
            Column {
                Text(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp), text = "One")
                Text(text = "Two")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard(name: String = "hello again") {
        MessageCard("Android")
    }

}
