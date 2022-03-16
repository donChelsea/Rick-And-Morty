import com.apollographql.apollo3.ApolloClient
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery

class CharacterModel {

    interface OnFinishedListener {
        suspend fun onResultSuccess(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null
        )

        suspend fun onResultFailure(error: String)
    }

    suspend fun requestCharacters(onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharactersQuery()).execute()
        if (response.data != null) {
            response.data?.characters?.results?.let {
                onFinishedListener.onResultSuccess(charactersUpdate = it, characterUpdate = null)
            }
        } else {
            onFinishedListener.onResultFailure("Could not retrieve all characters.")
        }
    }

    suspend fun requestCharacter(id: String, onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharacterQuery(id)).execute()
        if (response.data != null) {
            response.data?.character.let {
                onFinishedListener.onResultSuccess(characterUpdate = it, charactersUpdate = null)
            }
        } else {
            onFinishedListener.onResultFailure("Could not retrieve character: $id.")
        }
    }

    companion object {
        private val apolloClient = ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()
    }
}

