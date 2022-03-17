# Rick-And-Morty App

<h3>Architecture: MVP</h3>

<p align="center">
  <img src="https://media.geeksforgeeks.org/wp-content/uploads/20201024233154/MVPSchema.png" />
</p>
Source: https://www.geeksforgeeks.org/mvp-model-view-presenter-architecture-pattern-in-android-with-example/<br><br>
The Model-View-Presenter architecture pattern is useful because of the ability to efficiently separate the roles of each class. It prevents coupling the views (UI layer) and the business logic that would be placed in a data layer. MVP also helps with unit testing because there wouldn’t be a need to create Android SDK components to be able to test logical parts of the app.

<h3>Models</h3> 

<b>CharacterModel</b><br>
The component is the model that controls the retrieval and updating of characters from the Rick and Morty graphQL api. It houses a private instance of the Kotlin Apollo client performs the data retrieval within suspend functions that are called by a presenter.

<b>SharedPreferencesModel</b><br>
This component behaves similarly to the CharacterModel in that it is the only
source of truth for data collected and stored in shared preferences. It holds a private instance of shared preferences and the editor to be able to get all the characters that the user has saved as a favorite, add new ones, or delete characters.

These models each have their own interface which acts as a listener for retrieved data. It provides the requested data in a success function or an error message if the request fails. 

<h3>Presenters</h3>
Each presenter class takes a view and a model as constructor parameters. This is so that the presenter can be able to share data between the model and the view without those two components knowing about each other. The presenter will retrieve data from the model(s) when the corresponding view makes a call for it. It will then use the overridden functions from the OnFinishedListener within the model to update the view with its newly retrieved data.<br><br>

<b>MainPresenter</b><br>
This class is used to get all the characters in Rick and Morty from the CharacterModel. It will present the data onto the main activity. 

<b>DetailsPresenter</b><br>
This class is used to get a single character from the CharacterModel. It will present the data onto the details fragment. 

<b>FavoritesPresenter</b><br>
This class is used to get saved data from the SharedPreferencesModel. It will allow all the data to be shown and deleted from the favorites activity and updated from the details fragment. 

<h3>Views</h3>

<p>
  <img src="https://user-images.githubusercontent.com/44322627/158835253-c9e2396e-0289-4670-ba38-956a881bdc55.png" width="200" height="400"/>
  
  <img src="https://user-images.githubusercontent.com/44322627/158836471-e0323493-2a82-48be-a7ba-e65994506b2e.png" width="200" height="400"/>
  
  <img src="https://user-images.githubusercontent.com/44322627/158837041-490e38d1-3a66-4589-aa24-6c6d2132dfba.png" width="200" height="400"/>
  
  <img src="https://user-images.githubusercontent.com/44322627/158837159-1d1ccd83-a53f-4c67-a2fb-456dc0e2c059.png" width="200" height="400"/>
</p>

<b>Contract.View</b><br>
This is the interface that houses the functions each view will inherit in order to communicate with their presenters. It includes functions to control a progress bar, set retrieved data in the format they will be provided in, and show errors on the screen. 

<b>MainActivity</b><br>
This activity updates a recyclerview with data from the getAllCharacters function in the MainPresenter. It uses the CharactersAdapter with a click listener to show the data and respond to click events on the recyclerview. This activity also has an options menu with a favorites item in order to navigate to the user’s stored favorites. 

<b>Details Fragment</b><br>
A bottom sheet fragment that will receive a character id in order to make a request to the DetailsPresenter for that specified character’s detailed information. It will also use the FavoritesPresenter to add that character into shared preferences if the user chooses to. 

<b>Favorites Activity</b><br>
This activity will request and present the saved favorite!
 characters from the FavoritesPresenter. It will update its recyclerview with the information. The recyclerview uses a click listener in case the user wants to delete the entry. An alert dialog will appear to confirm the user’s decision. 
