package com.example.codebrahma.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PythonWebCompilerApi {

    @POST("/run")
    fun runPythonCode(@Body request: CodeRequest): Call<CodeResponse>

    @POST("/install")
    fun installPythonPackage(@Body request: PackageRequest): Call<PackageResponse>
}

data class CodeRequest(val code: String)
data class CodeResponse(val output: String)

data class PackageRequest(val packageName: String)  // Renamed to avoid using 'package' keyword
data class PackageResponse(val output: String)
