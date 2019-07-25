package br.com.zapiti.translateexpression.views

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.zapiti.translateexpression.R
import br.com.zapiti.translateexpression.adapters.SimpleFilterAdapter
import br.com.zapiti.translateexpression.utils.ConstantUtil
import br.com.zapiti.translateexpression.utils.Language
import br.com.zapiti.translateexpression.utils.TypeLanguageUtils
import kotlinx.android.synthetic.main.activity_type_language.*

class TypeLanguageActivity : AppCompatActivity() {

    lateinit var mtypeFilter: String
    var mTypeLanguageList = ArrayList<Language>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_language)

        initData()

        initComponent()


    }

    private fun initData() {
        mtypeFilter = intent.getStringExtra(ConstantUtil.TPFILTER) ?: ""
        initList()
    }

    private fun initComponent() {
        initToolbar()

        activity_type_language_recycleview.adapter = SimpleFilterAdapter(mTypeLanguageList)

        activity_type_language_recycleview.adapter?.notifyDataSetChanged()

        (activity_type_language_recycleview.adapter as SimpleFilterAdapter).onClickItem {
            setResultIntent(it)
        }
        initSearch()

    }

    private fun initList() {
        mTypeLanguageList.clear()
        mTypeLanguageList.addAll(TypeLanguageUtils.getLanguagens())
    }

    private fun initToolbar() {
        toolbar_main_type_languagem.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar_main_type_languagem.inflateMenu(R.menu.search)
        toolbar_main_type_languagem.setNavigationOnClickListener(
            object : DialogInterface.OnClickListener,
                View.OnClickListener {
                override fun onClick(p0: View?) {
                    onBackPressed()
                }

                override fun onClick(p0: DialogInterface?, p1: Int) {
                    onBackPressed()
                }
            }
        )
    }

    //region <! Função para retornar a intent !>
    private fun setResultIntent(generic: Language) {

        if (mtypeFilter == ConstantUtil.CONVERTER) {
            val resultIntent = Intent()
            resultIntent.putExtra(ConstantUtil.TPFILTER, generic)
            setResult(1, resultIntent)

        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(ConstantUtil.TPFILTER, generic)
            setResult(0, resultIntent)
        }

        super.onBackPressed()
    }

    //endregion
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initSearch() {
        val searchView =
            toolbar_main_type_languagem.menu.findItem(R.id.menu_activity_profile_edit_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    val filter =
                        mTypeLanguageList.filter { i -> i.name.contains(other = text ?: "", ignoreCase = true) }
                    if (text?.isBlank() == true) {
                        initList()
                        activity_type_language_recycleview.adapter?.notifyDataSetChanged()

                    } else {
                        mTypeLanguageList.clear()
                        mTypeLanguageList.addAll(filter)
                        activity_type_language_recycleview.adapter?.notifyDataSetChanged()
                    }
                    return true
                }
            }
        )
    }


}
