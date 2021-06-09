package com.mindorks.framework.widgetpertemuan12.widgetTimer

class saveMessage() {


    private var lstMessage = arrayListOf(
        "Hello",
        "Have a nice Day",
        "Sunny Day",
        "Remember to be Happy"
    )
    private var index = -1

    fun addMessage(str: String) {
        lstMessage.add(str)
    }

    fun removeMessage(str: String){
        lstMessage.remove(str)
    }

    fun backToStart() { index = -1}

    fun getMessage() : String {
        if(lstMessage.size==0)
            return "Kosong"
        if(index+1==lstMessage.size)
            backToStart()
        index+=1
        return lstMessage.get(index)
    }
}