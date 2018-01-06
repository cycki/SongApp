package pl.mkwiecinski.songapp.shared

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import java.lang.ref.WeakReference

class ListChangedAdapterCallback<T>(adapter: RecyclerView.Adapter<*>) : ObservableList.OnListChangedCallback<ObservableList<T>>() {
    private val adapterRef = WeakReference(adapter)

    override fun onItemRangeInserted(sender: ObservableList<T>?,
                                     positionStart: Int,
                                     itemCount: Int) {
        adapterRef.get()?.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onChanged(sender: ObservableList<T>?) {
        adapterRef.get()?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: ObservableList<T>?,
                                    positionStart: Int,
                                    itemCount: Int) {
        adapterRef.get()?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeRemoved(sender: ObservableList<T>?,
                                    positionStart: Int,
                                    itemCount: Int) {
        adapterRef.get()?.notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: ObservableList<T>?,
                                  fromPosition: Int,
                                  toPosition: Int,
                                  itemCount: Int) {
        onItemRangeRemoved(sender, fromPosition, itemCount)
        onItemRangeInserted(sender, toPosition, itemCount)
    }
}
