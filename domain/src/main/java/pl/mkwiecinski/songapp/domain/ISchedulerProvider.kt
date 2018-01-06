package pl.mkwiecinski.songapp.domain

import io.reactivex.Scheduler

interface ISchedulerProvider {
    val worker: Scheduler
    val postExecution: Scheduler
}
