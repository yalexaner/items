# items
## Usage and UI
Card Component with items information, Scrolling list with all the Orders, and a BottomSheet to add an Order

<img src="https://media.giphy.com/media/vUA6C5rdbmfxRE9QV4/giphy.gif">

## What I use
Coroutines: data is being loaded on the IO thread, so the UI won't freeze.

Room and LiveData: on app startup getting Orders from DB while showing LoadingScreen. After data is loaded LiveData updates the value what causes the recomposition, and the LoadingScreen is replaced with the ListScreen.

ViewModel: saves data on Activity being destroyed (on screen rotation or light/dark mode changes).

Jetpac Compose.
