package com.example.rickandmorty

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
    }

    interface Model {
        interface OnFinishedLiOstener {
            fun onFinished(string: String?)
        }

        suspend fun onCharactersRequested()

        suspend fun onCharacterRequested(id: String)
    }

    interface Presenter {

        suspend fun getData()

        fun onDestroy()
    }
}