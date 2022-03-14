package com.example.rickandmorty

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
    }

    interface Model {
        interface OnFinishedListener {
            fun onFinished(string: String?)
        }

        fun getNextCourse(onFinishedListener: OnFinishedListener?)
    }

    interface Presenter {
        interface OnCharactersRequested {
            suspend fun fetchCharacters()
        }

        interface OnCharacterRequested {
            suspend fun fetchCharacter(id: String)
        }

        fun onDestroy()
    }
}