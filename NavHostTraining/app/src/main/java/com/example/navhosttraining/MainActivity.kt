package com.example.navhosttraining

import android.R.attr.label
import android.R.attr.singleLine
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhosttraining.ui.theme.NavHostTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavHostTrainingTheme {
                Surface(modifier = Modifier.fillMaxSize())
                {
                    NavHostTrainingApp()
                }
            }
        }
    }
}

@Composable
fun NavHostTrainingApp(modifier: Modifier = Modifier) {

    var textFieldValue by rememberSaveable { mutableStateOf("")}

    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = stringResource(R.string.title_info))
        EditableTextField(
            label = R.string.textfield_label,
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            )
        )
        Button(
            onClick = {}
        ) {
            Text(text=stringResource(R.string.button_title))
        }


    }


}

@Composable
fun EditableTextField(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier

) {
    TextField(
        value = value,
        modifier = Modifier,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true
        )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavHostTrainingTheme {
        NavHostTrainingApp()
    }
}