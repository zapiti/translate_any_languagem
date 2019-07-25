package br.com.zapiti.translateexpression.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

object BufferUtil {
     fun stringBuffer(con: HttpURLConnection): StringBuffer {
        val buffer = BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val response = StringBuffer()
        inputLine = buffer.readLine()
        while (inputLine != null) {
            if (inputLine.getOrNull(0) != null) {
                response.append(inputLine)
            }
            inputLine = buffer.readLine()
        }
        buffer.close()
        return response
    }
}