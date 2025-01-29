package com.example.gridpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gridpractice.ui.theme.GridPracticeTheme
import com.example.gridpractice.model.Topic
import com.example.gridpractice.R
import com.example.gridpractice.data.DataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GridPracticeTheme {
                GridPracticeApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun GridPracticeApp(modifier: Modifier = Modifier) {
    GridPracticeList(DataSource.topics, modifier)
}

@Composable
fun GridPracticeList(topicList: List<Topic>, modifier: Modifier = Modifier) {
    val chunkSize: Int = ((topicList.size + 1) / 2)
    val chunkedTopics = topicList.chunked(chunkSize)
    val chunkedColumn1 = chunkedTopics[0]
    val chunkedColumn2 = chunkedTopics[1]

    LazyColumn(modifier = modifier) {
        item {
            Row {
                Column(modifier = Modifier.weight(1f).padding(2.dp)) {
                    chunkedColumn1.forEach { topic ->
                        GridTopicItem(topic = topic, modifier = Modifier)
                    }
                }
                Column (modifier = Modifier.weight(1f).padding(2.dp)){
                    chunkedColumn2.forEach { topic ->
                        GridTopicItem(topic = topic, modifier = Modifier)
                    }
                }
            }
        }
    }
}

@Composable
fun GridTopicItem( topic: Topic,modifier: Modifier = Modifier) {
    Card(modifier = modifier){
        Row() {
            Image(painter = painterResource(id = topic.imageId), contentDescription = stringResource(topic.name))
            Column(modifier = Modifier.padding(6.dp).fillMaxWidth()) {
                Text(text = stringResource(topic.name))
                Text(text = topic.numOfCourses.toString())
            }

        }
    }
}


@Preview
@Composable
fun GridPracticePreview() {
    GridPracticeList(DataSource.topics)
}
