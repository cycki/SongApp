package pl.mkwiecinski.songapp.adapters

import android.databinding.BindingAdapter
import android.support.v4.widget.ContentLoadingProgressBar
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import pl.mkwiecinski.rxcommand.CommandState
import pl.mkwiecinski.rxcommand.RxCommand
import pl.mkwiecinski.songapp.shared.execute

@BindingAdapter("android:onClick") fun <T> bindOnClick(view: Button, command: RxCommand<Unit, T>?) {
    view.setOnClickListener {
        command?.execute()
    }
}

@BindingAdapter("android:enabled") fun <T> bindEnabled(view: View, command: RxCommand<Unit, T>?) {
    command?.executing?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
        view.isEnabled = it == CommandState.Finished
    }
}

@BindingAdapter("android:visibility") fun <T> bindVisibility(view: View,
                                                             command: RxCommand<Unit, T>?) {
    command ?: return
    command.executing.observeOn(AndroidSchedulers.mainThread()).subscribe {
        view.visibility = when (it) {
            CommandState.Finished -> View.VISIBLE
            else -> View.GONE
        }
    }
}

@BindingAdapter("android:visibility") fun <T> bindProgressbarVisibility(view: ProgressBar,
                                                                        command: RxCommand<Unit, T>?) {
    command ?: return
    val action = if (view is ContentLoadingProgressBar) {
        Consumer<CommandState> {
            when (it) {
                CommandState.Finished -> view.hide()
                else -> view.show()
            }
        }
    } else {
        Consumer {
            view.visibility = when (it) {
                CommandState.Finished -> View.GONE
                else -> View.VISIBLE
            }
        }
    }
    command.executing.observeOn(AndroidSchedulers.mainThread()).subscribe(action)

}

@BindingAdapter("android:visibility") fun <T> bindVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}