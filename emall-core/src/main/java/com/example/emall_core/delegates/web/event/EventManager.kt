package com.example.emall_core.delegates.web.event


/**
 * Created by lixiang on 2018/2/27.
 */
class EventManager private constructor() {

    private object Holder {
        val INSTANCE = EventManager()
    }

    fun addEvent(name: String, event: Event): EventManager {
        EVENTS[name] = event
        return this
    }

    fun createEvent(action: String): Event {
        val event: Event? = EVENTS[action]
        if (event == null){
            return UndefindEvent()
        }
        return event
    }

    companion object {

        private val EVENTS = HashMap<String, Event>()

        val instance: EventManager
            get() = Holder.INSTANCE
    }
}
