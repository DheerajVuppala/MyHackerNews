package com.test.hackernews.ux

interface ActionListener {
    /**
     * Called by a Fragment to request the Activity perform an [action]
     * @param action should be unique by Fragment and action.
     *  Format is ClassName::class.java.simpleName + ".actionName"
     *  where ".actionName" is discriptive of the action
     * @param data optional data to be passed back to the Activity. It is the Activities
     * responsibility to decode data into the appropriate class based on the action
     */
    fun onAction(action : String, data : Any? = null)

    /**
     * Custom Exception to handle exceptions related to ActionListener.
     * @param message is the message to be passed while raising exception.
     */
    class ActionListenerException(override var message: String): Exception(message)
}