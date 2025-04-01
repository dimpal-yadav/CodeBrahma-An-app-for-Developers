//suggests the python code in offline format
package com.example.codebrahma

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun OfflineMode() {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var output by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier.padding(top = 56.dp)) {
        Text(text = "Enter Python Code:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Python Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (code.text.isNotBlank()) {
                    output = PythonExecutor.runPythonCode(code.text)
                } else {
                    Toast.makeText(context, "Enter only Python code!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Output")
        }
        Text(text = "Output:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        SelectionContainer {
            Text(text = output, color = Color.Green, modifier = Modifier.padding(4.dp))
        }
    }
}