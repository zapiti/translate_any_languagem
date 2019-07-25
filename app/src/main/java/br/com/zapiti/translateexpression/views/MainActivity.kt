package br.com.zapiti.translateexpression.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.zapiti.translateexpression.R
import br.com.zapiti.translateexpression.repositories.TranslateRepository
import br.com.zapiti.translateexpression.utils.ConstantUtil
import br.com.zapiti.translateexpression.utils.ConstantUtil.TPFILTER
import br.com.zapiti.translateexpression.utils.Language
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var typeConverter: Language = Language.PORTUGUESE
    var typeConverted: Language = Language.ENGLISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()

    }

    private fun initComponent() {
        editText_response_type.setOnClickListener {
            val intents = Intent(this, TypeLanguageActivity::class.java)
            intents.putExtra(TPFILTER, ConstantUtil.CONVERTER)
            startActivityForResult(intents, 0)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        your_input_type.setOnClickListener {
            val intents = Intent(this, TypeLanguageActivity::class.java)
            intents.putExtra(TPFILTER, ConstantUtil.CONVERTED)
            startActivityForResult(intents, 1)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        button.setOnClickListener {
            callTranslate()
        }
        setFieldsValues()
    }


    fun setFieldsValues() {
        editText_response_type.setText(typeConverted.name)
        your_input_type.setText(typeConverter.name)
    }

    private fun callTranslate() {
        val text = your_input.text.toString()
        showProgressBar()
        TranslateRepository.translateByLanguage(
            langFrom = typeConverter.descrption,
            langTo = typeConverted.descrption,
            textInputed = text
        ) { response ->
            hideProgressBar()
            responseText.setText(response)
        }

    }

    private fun showProgressBar() {
        progress_bar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar?.visibility = View.GONE
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == 0) {
                typeConverter = data.getSerializableExtra(TPFILTER) as Language
                setFieldsValues()

            } else {
                typeConverted = data.getSerializableExtra(TPFILTER) as Language
                setFieldsValues()
            }
        }
    }
}
