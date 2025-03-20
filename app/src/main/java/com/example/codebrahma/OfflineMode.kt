package com.example.codebrahma

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codebrahma.api.QnARequest
import com.example.codebrahma.viewmodel.QnaViewModel

@Composable
fun OfflineMode(viewModel: QnaViewModel = viewModel()) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    val qnaList by viewModel.qnaList.collectAsState()
    val response by viewModel.answer.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(top=96.dp), verticalArrangement = Arrangement.Top) {
        Text("Offline AI Debugging", fontSize = 30.sp, color = Color.Black)
        BasicTextField(
            value = question,
            onValueChange = { question = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { viewModel.askQuestion(question) }),
            singleLine = true
        )
        Button(onClick = { viewModel.askQuestion(question) }, modifier = Modifier.fillMaxWidth()) {
            Text("Ask")
        }
        if (response != null) {
            Text("Answer: $response", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(qnaList) { qna ->
                QnAItem(qna, viewModel)
            }
        }
    }
}
@Composable
fun QnAItem(qna: QnARequest, viewModel: QnaViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Q: ${qna.question}", fontSize = 18.sp, color = Color.Black)
            Text(text = "A: ${qna.answer}", fontSize = 16.sp, color = Color.DarkGray)

            Row {
                Button(onClick = { viewModel.deleteQnA(qna.question) }) {
                    Text("Delete")
                }
            }
        }
    }
}
