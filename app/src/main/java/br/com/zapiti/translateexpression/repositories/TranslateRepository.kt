package br.com.zapiti.translateexpression.repositories

import br.com.zapiti.translateexpression.utils.BufferUtil.stringBuffer
import br.com.zapiti.translateexpression.utils.DoAsync
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


object TranslateRepository {


    fun translateByLanguage(
        langFrom: String,
        langTo: String,
        textInputed: String,
        onCompletion: (response: String) -> Unit
    ) {

        var resp: String? = null
        DoAsync(doInBackgroundTask = {
            try {
                val response = httpConection(
                    langFrom = langFrom,
                    langTo = langTo,
                    textInputed = textInputed
                )
                resp = response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                onCompletion(e.message ?: "Falha na conversão")
            }
        }, onPostExecuteTask = {
            var temp = ""
            if (resp == null) {
                onCompletion("Network Error")
            } else {
                try {
                    val main = JSONArray(resp)
                    val total = main.get(0) as JSONArray
                    for (i in 0 until total.length()) {
                        val currentLine = total.get(i) as JSONArray
                        temp += currentLine.get(0).toString()
                    }
                    onCompletion(temp)
                } catch (e: Exception) {
                    onCompletion(e.localizedMessage ?: "Erro")
                    e.printStackTrace()
                }
            }
        }).execute()
    }

    private fun httpConection(
        langFrom: String,
        langTo: String,
        textInputed: String
    ): StringBuffer {
        val url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" +
                langFrom + "&tl=" + langTo + "&dt=t&q=" + textInputed
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.setRequestProperty("User-Agent", "Mozilla/5.0")
        return stringBuffer(con)
    }
}


