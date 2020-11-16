
# android-random-persons  
an basic list of random persons for android – the app fetches users from [Random User Generator](https://randomuser.me/).  
  
## UI and Design  
  
Master and Detail Layout.  
  
It has caters 2 screen types:  
1. Screens with 600dp and above will be considered as tablet mode.  
It always show the Master List on the left and Details on the right.  
  
2. Anything below 600dp. Initially shows a the master list and  
opens details screen on item tap.  
  
## Architecture: MVVM  
  
The choice was made by as this is the recommended patter from Google's jetpack architecture components.  
  
It is also a lot easier to construct and follow the Single Responsibility Principle.  
  
1. Model - represents the data layer of application which contains source of data.  
2. ViewModel - mediates between the View and Model, and mostly contains the business logic.  
3. View - represents the UI layer and listens for state changes from the ViewModel.  
  
### Dependency Injection  
  
Used Android's Hilt to automated dependency Injection  
  
### Navigation  
  
I used the Single Activity architecture and Navigation Component  
from Jetpack's architecture component to implement screen navigations.  
  
### Programming  
  
I implemented the observer pattern using kotlin coroutines' Flow.  
  
Used kotlin coroutine's suspending function feature for easier implementation  
of threading and asynchronous task.  
  
## Persistence  
  
I used Android Room to have easier interface to Android's sqlite for local data storage.  
  
I implemented repository pattern to centralize the data from both local and network sources.  
  
I implemented one of the most common data access strategy to access data:  
  
1. On query return observable local data.  
2. Invoke API network call.  
3. Save response to local storage.  
4. Updates on the local storage are automatically available for the subscribers.  
  
Also used a helper data class (Resource) to encapsulate data according to their states  
which makes it easier to consume.  
  
### API  
  
[Random User Generator](https://randomuser.me/) API was used.  
  
Consumed using Retrofit Network Library and mapped data using Moshi.  
  
Created a service with 1 function:  
  
1. getRandomUsers - which accept a map of key-values pairs like results, seed, and inc.  
  
### Tests  
I made unit testing for both network api call and repository since I  
created the data layer first.