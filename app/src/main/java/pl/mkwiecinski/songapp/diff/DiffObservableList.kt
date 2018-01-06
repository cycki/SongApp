package pl.mkwiecinski.songapp.diff

import android.databinding.ListChangeRegistry
import android.databinding.ObservableList
import android.os.Handler
import android.os.Looper
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import java.util.*


class DiffObservableList<T>(private val callback: DiffCallback<T>,
                            private val detectMoves: Boolean = true) : AbstractList<T>(),
        ObservableList<T> {

    private val LIST_LOCK = Any()
    private var list = emptyList<T>()
    private val listeners = ListChangeRegistry()
    private val listCallback = ObservableListUpdateCallback()

    var value: List<T>
        get() = this
        set(value) {
            val diff = calculateDiff(value)
            Handler(Looper.getMainLooper()).post {
                update(value, diff)
            }
        }

    @MainThread private fun update(newItems: List<T>, diffResult: DiffUtil.DiffResult) {
        synchronized(LIST_LOCK) {
            list = newItems
        }
        diffResult.dispatchUpdatesTo(listCallback)
    }

    @WorkerThread private fun calculateDiff(newItems: List<T>): DiffUtil.DiffResult {
        var frozenList = listOf<T>()
        synchronized(LIST_LOCK) {
            frozenList = list.toList()
        }
        return doCalculateDiff(frozenList, newItems)
    }

    private fun doCalculateDiff(oldItems: List<T>, newItems: List<T>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldItems.size

            override fun getNewListSize() = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areItemsTheSame(oldItems[oldItemPosition],
                                                newItems[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areContentsTheSame(oldItems[oldItemPosition],
                                                   newItems[newItemPosition])
            }
        }, detectMoves)
    }

    override fun addOnListChangedCallback(listener: ListChangedCallback<T>) {
        listeners.add(listener)
    }

    override fun removeOnListChangedCallback(listener: ListChangedCallback<T>) {
        listeners.remove(listener)
    }

    override fun get(index: Int) = list[index]

    override val size: Int
        get() = list.size

    internal inner class ObservableListUpdateCallback : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any) {
            listeners.notifyChanged(this@DiffObservableList, position, count)
        }

        override fun onInserted(position: Int, count: Int) {
            modCount += 1
            listeners.notifyInserted(this@DiffObservableList, position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            modCount += 1
            listeners.notifyRemoved(this@DiffObservableList, position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            listeners.notifyMoved(this@DiffObservableList, fromPosition, toPosition, 1)
        }
    }
}

typealias ListChangedCallback<T> = ObservableList.OnListChangedCallback<out ObservableList<T>>