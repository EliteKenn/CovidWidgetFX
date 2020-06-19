![CovidWidget](https://user-images.githubusercontent.com/53195969/85088742-d2f63c80-b1ae-11ea-98c4-c89abfa70930.jpg)


This widget shows the coronavirus(covid-19) positive cases, recovered cases, and deaths in the world and shows the cases for a given country.

I queried an online API to get coronavirus statistics data. Once the data is available, we can pass the data into the JavaFX GUI and display the data. For GUI development, I used SceneBuilder. I then designed the GUI as an FXML file.

I then converted the JavaFX window(stage) to appear and act like an actual widget. I then made use of the java scheduler to schedule automatic data-refresh at fixed intervals.

First, I used the Retrofit2 library for HTTP communication. It allowed me to make GET requests to the API and it responded with JSON string. I was then able to parse the string to java POJO and use it as data for the widget.

Then I used a design library called Ikonli to add an icon to the app. I also made use of CSS styling to properly style the widget. I have used a monospace font named 'Hack'. 

The first challenge is to make the JavaFX stage look like a widget. For a widget, there should no taskbar icons and the stage should not be decorated. So I implemented a technique to hide both the taskbar icon and stage decoration. I also added the click-and-drag support for the widget. I also handled the automatic refresh of the widget data. Using ScheduledExecutorService, I can schedule a function to be called repeatedly at fixed intervals. I created a single-threaded scheduled executor service.

Then I made use of the GSON library to convert a java model POJO class to JSON string and vice versa. 3 values will be stored in the settings file.

![settings](https://user-images.githubusercontent.com/53195969/85088801-f4efbf00-b1ae-11ea-9729-bda8cf98f8f7.png)


1. The interval at which the scheduler should refresh the data from API.
2. The country of which we need the COVID data
3. Country code

When the application starts, these values will be read from the file and used. If the file doesn't already exist, then it will be created with default values.
