package pl.mkwiecinski.songapp.ui.dialogs

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.app.AlertDialog
import pl.mkwiecinski.songapp.R
import pl.mkwiecinski.songapp.domain.extensions.enumSetOf
import pl.mkwiecinski.songapp.domain.usecases.SourceType
import pl.mkwiecinski.songapp.shared.value
import java.util.*

class FiltersDialogFragment(context: Context,
                            private val selected: ObservableField<EnumSet<SourceType>>) {
    private val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)

    private val tempValue = selected.value?.clone() ?: enumSetOf<SourceType>()

    private val source = SourceType.values()

    init {
        val values = source.map {
            when (it) {
                SourceType.Local -> R.string.local
                SourceType.Remote -> R.string.remote
            }
        }.map(context::getString).toTypedArray()

        val checked = SourceType.values().map { selected.value.orEmpty().contains(it) }.toBooleanArray()

        builder.setMultiChoiceItems(values, checked, { _, which, isChecked ->
            source[which].let {
                if (isChecked) {
                    tempValue.add(it)
                } else {
                    tempValue.remove(it)
                }
            }
        })
        builder.setPositiveButton(R.string.apply, { _, _ ->
            selected.value = tempValue
        })
        builder.setNegativeButton(R.string.cancel, { _, _ -> })
        builder.setTitle(R.string.choose_source)
    }


    fun show() {
        builder.show()
    }
}
