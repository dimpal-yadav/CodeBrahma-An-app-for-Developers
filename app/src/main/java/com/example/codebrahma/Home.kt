package com.example.codebrahma

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Home() {
    val pythonSnippets = listOf(
        """
            def greet(name):
                return f"Hello, {name}!"
            
            print(greet("Aditya"))
        """.trimIndent(),

        """
            for i in range(5):
                print("CodeBrahma Rocks!")
        """.trimIndent(),

        """
            x = [i*i for i in range(10)]
            print(x)
        """.trimIndent(),

        """
            def factorial(n):
                return 1 if n == 0 else n * factorial(n - 1)
            
            print(factorial(5))
        """.trimIndent()
    )

    val selectedSnippet = remember { pythonSnippets.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(top=96.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        QuoteImageCard()
        Spacer(modifier = Modifier.padding(16.dp))
        CodeSnippetCard(selectedSnippet)
        GitHubStatsWeb()
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun QuoteImageCard() {
    val htmlContent = """
        <html style="margin:0;padding:20px;background-color:#121212;">
        <body style="margin:0;padding:10px;text-align:center;background-color:#121212;">
            <img src="https://quotes-github-readme.vercel.app/api?type="vertical"&theme=merko" alt="Dev Quote"
                 style="width:50%;height:auto;" />
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadDataWithBaseURL(
                    null,
                    htmlContent,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}


@Composable
fun CodeSnippetCard(snippet: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Code Snippet of the Day",
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = snippet,
                color = Color(0xFFCE93D8),
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun GitHubStatsWeb() {
    val html = """
        <html style="background-color:#121212;">
  <body style="margin:0; padding:0;">
    <div align="center" style="width: 100%;">
      <table width="100%" style="margin-top:16px;">
        <tr>
          <td width="100%" valign="middle" style="padding-bottom: 16px;">
            <!-- GitHub Streak Card -->
            <a href="https://github.com/Aditya948351">
              <img src="https://github-readme-streak-stats.herokuapp.com/?user=Aditya948351&theme=dracula" 
                   alt="GitHub Streak" width="100%" />
            </a>
          </td>
        </tr>
        <tr>
          <td width="100%" valign="middle">
            <!-- GitHub Stats Card -->
            <a href="https://github.com/Aditya948351">
              <img src="https://github-readme-stats.vercel.app/api?username=Aditya948351&show_icons=true&show=reviews,prs_merged,prs_merged_percentage&theme=dark" 
                   width="100%" />
            </a>
          </td>
        </tr>
      </table>
    </div>
  </body>
</html>

    """.trimIndent()

    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .height(300.dp)
        .padding(vertical = 16.dp))
}
