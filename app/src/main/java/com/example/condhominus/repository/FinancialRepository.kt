package com.example.condhominus.repository

import com.example.condhominus.services.CondhominusService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FinancialRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://condhominus.somee.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pdfBytes = """
    %PDF-1.7
    %\x93\x8c\x8b\x9e
    3 0 obj
    <</Type /Page
    /Parent 1 0 R
    /Resources 2 0 R
    /Contents 4 0 R
    /MediaBox [0 0 612 792]>>
    endobj
    2 0 obj
    <</ProcSet [/PDF /Text /ImageB /ImageC /ImageI]
    /Font <<
    /F1 5 0 R
    >>
    /XObject <<
    >>
    >>
    endobj
    4 0 obj
    <</Length 58>>
    stream
    BT
    /F1 24 Tf
    100 700 Td
    ( ) Tj
    ET
    endstream
    endobj
    5 0 obj
    <</Type /Font
    /Subtype /Type1
    /BaseFont /Helvetica
    /Encoding /WinAnsiEncoding>>
    endobj
    1 0 obj
    <</Type /Pages
    /Kids [3 0 R]
    /Count 1
    >>
    endobj
    6 0 obj
    <</Type /Catalog
    /Pages 1 0 R
    >>
    endobj
    xref
    0 7
    0000000000 65535 f 
    0000000323 00000 n 
    0000000010 00000 n 
    0000000391 00000 n 
    0000000203 00000 n 
    0000000458 00000 n 
    0000000503 00000 n 
    trailer
    <</Size 7
    /Root 6 0 R
    >>
    startxref
    608
    %%EOF
""".trimIndent().toByteArray()

    private val service = retrofit.create(CondhominusService::class.java)

    fun getBillet(): ByteArray {
        return pdfBytes
//        return withContext(Dispatchers.Default) {
//            processData(service.getBillet())
//        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body()
        else null
    }
}