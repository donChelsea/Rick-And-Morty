package com.example.rickandmorty

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setString(string: String?)
    }

    interface Model {
        interface OnFinishedListener {
            fun onFinished(string: String?)
        }

        fun getNextCourse(onFinishedListener: OnFinishedListener?)
    }

    interface Presenter {
        interface OnNetworkCall {
            suspend fun fetchCharacters()
        }

        fun onDestroy()
    }
}