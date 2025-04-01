package com.example.codebrahma

import org.python.util.PythonInterpreter
import java.io.ByteArrayOutputStream
import java.io.PrintStream

object PythonExecutor {
    fun runPythonCode(code: String): String {
        val interpreter = PythonInterpreter()
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        interpreter.setOut(printStream)
        interpreter.setErr(printStream)

        return try {
            interpreter.exec(code) // Run Python code
            outputStream.toString() // Return output
        } catch (e: Exception) {
            "Error: ${e.message}" // Return error message
        }
    }
}
