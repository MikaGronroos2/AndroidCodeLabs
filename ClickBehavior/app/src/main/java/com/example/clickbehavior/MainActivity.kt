package com.example.clickbehavior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clickbehavior.ui.theme.ClickBehaviorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClickBehaviorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonTreeApplication(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun LemonTreeApplication(modifier: Modifier = Modifier) {
    LemonAppImage(
        modifier = Modifier.fillMaxSize().wrapContentSize()
    )
}


@Composable
fun LemonAppImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }
    var squeezeLimit by remember { mutableStateOf((2..5).random()) }
    val imageResult = when (result) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val stringResult = when (imageResult) {
        R.drawable.lemon_tree -> R.string.lemon_tree
        R.drawable.lemon_squeeze -> R.string.lemon_squeeze
        R.drawable.lemon_drink -> R.string.lemon_drink
        else -> R.string.lemon_restart
    }


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(imageResult), contentDescription = result.toString(),
            modifier = Modifier.clickable
        {
            if (result == 1) {
                result = 2
            } else if (result == 2) {
                squeezeCount++
                if (squeezeCount >= squeezeLimit) {
                    squeezeCount = 0
                    squeezeLimit = (2..5).random()
                    result++
                }
            } else if (result == 3) {
                result = 4
            } else {
                result = 1
            }
        })
        Text(text = stringResource(stringResult), fontSize = 22.sp)

    }



}