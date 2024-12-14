TV show Series 
TV Watch Series is an Android application designed to explore popular TV shows, search for your favorite series, and manage a personal watchlist. The project follows Clean Architecture principles, ensuring scalability, maintainability, and separation of concerns.
Features
1. Browse Popular TV Shows
    Discover the most popular TV shows via the MostPopular endpoint.
2. Search for TV Shows
    Search for TV shows using the Search endpoint and add them to your watchlist.
3. View Detailed Information
    Access detailed information about a specific TV show via the Details endpoint.
4. Manage Watchlist
    Add your favorite TV shows from the Most Popular or Search lists to a persistent local watchlist powered by Room Database.
      Architecture
   
This project adheres to the Clean Architecture pattern, which divides the application into three main layers:

1. Presentation Layer
 Responsibilities:
    * Provides a user interface (UI) to interact with the app.
    * Observes the state exposed by the ViewModel and updates the UI accordingly.
    * Implements UI logic while delegating business logic to the domain layer.
      
2. Domain Layer
 Components: Use cases, Models, and Interfaces
Responsibilities:
     Contains the business logic of the application.
     Defines interfaces for repository interactions, ensuring loose coupling between data and presentation layers.
   
4. Data Layer
    Technologies:
     Retrofit: For remote API communication.
     Room Database: For local data storage.
     Dagger Hilt: For dependency injection.
 Responsibilities:
     Fetches data from the remote API and provides it to the domain layer.
     Stores and retrieves local data via Room.

   
Dependencies
    Dagger Hilt: Simplifies dependency injection.
    Retrofit: Simplifies API communication.
    Room: Provides robust local database storage.
    Coroutines & Flow: For managing asynchronous programming and reactive streams.
    OkHttp: For HTTP client and interceptors.
    
![images](https://github.com/user-attachments/assets/eb1303f8-9849-410a-96e7-42b232b4e0ac)
    

