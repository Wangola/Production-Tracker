
# Production Tracker Project

*Production Tracker Description -*
This Production Tracker project provides any wanting facility the solution to categorize products and record products into a range of databases while allowing employees to create unique products under their supervision. Employees can easily interact with this projects JavaFX built GUI to create accounts and login when wanting to interact with their employer’s product line. The product line format is simple, it displays any existing product found and employees can enter their new wanted product which is displayed in real time. With these existing products, employees can avoid headaches and increase productivity since they can record any product from the existing list with their desired amount. Once the production line is complete and all wanted products are recorded a descriptive and unique production log is created for employees to view.

* What is this project?
	* This is a production tracker project that was built using JavaFX and focused on utilizing H2 databases to store values entered by employees. Employees are allowed to store products into these databases and produce an updated production log if changes occurs. Coding style used for this program was object oriented with its focus on inheritance, abstraction, encapsulation, and polymorphism. (4 pillars)
* Who made this project?
	* Author: William Angola
	* Created independently with some tips and hints from: TA's, Professor, and class mates.
* When did you make it?
	* This was made through out my fall semester in 2020,  as a Junior I felt slightly experienced when starting this project but after multiple errors and learning of concepts I can gracefully say I am moderately experienced. You can always learn something new.
* Why did you make it?
	* This was made as a semester long project which had it's ups and downs of productivity. This project reflects me as a person who focuses too much on functionality and not enough on colors and display, which I need to work on. It taught me how to deal with programming frustration with meditation and question/write up logical steps rather than jumping right in.
* Where did you make it?
	* Thankfully in the comfort of my own home due to covid, but the university I attend is Florida Gulf Coast University. This project was assigned in my COP 3003 Object Oriented Programming course.


## Demonstration

![Production Project Final Gif](pictures/Production%20Project%20Final.gif)


## Documentation
[JavaDoc](https://wangola.github.io/Production-Tracker/)

## Diagrams

![Class Diagram](pictures/Class%20diagram.PNG)

![Database Diagram](pictures/Database%20diagram.PNG)
## Getting Started

If you wish to run this project on your local machine. Follow these steps:
 * Download [Intellij IDEA Ultimate Version](https://www.jetbrains.com/idea/download/#section=windows) and download the latest version of [JavaFX](https://gluonhq.com/products/javafx/) .
 * Once both are download watch [this](https://www.youtube.com/watch?v=LFvRMmkXZk0) video to set up SDK files in IntelliJ. 
 * Once set up it is complete return to IntelliJ start up page and select (get from Version Control) it will ask for a URL and input the URL from this GitHub website and hit clone. 
 * Once project is cloned you are going to want to change one line of code in the Controller class.
 * Find the getDbPassword method and you are gonna want to change the path for the dbPassword.txt file to match to avoid a nullException error. 
	 * Example: 
		 * Current path: "C:\\Users\\William\\IdeaProjects\\Production\\src\\main\\java\\dbPassword.txt"
		 * Changed to: [your path] + \\dbPassword.txt
* Once all these steps are complete you should be able to run this project on your local machine.

## Built With
* IDE used: 
	* Intellij IDEA Ultimate 2020.2, focusing on Java development and created by JetBrains.
* Other software used:
	* Scene Builder to create GUI interactions.
* Resources used:
	* Cave of Programming, Stack OverFlow, Youtube.

## Contributing
If you wish to contribute to this project be sure to fork this repository and follow the steps in Getting Started to set up this project in your local device.  Once the Production Tracker project is working on your local device you can make any edits/additions to this project. Once satisfied upload edits/additions to your personal forked repository and make a New pull request that will be review by me.

## Author

* William Angola

## License
[License](License)


## Acknowledgments
* Web sites utilized:
	* Cave of Programming, Stack OverFlow, Youtube.
* Classmates who helped:
	* Jaysson Balbuena
	* Paul Basso
* Your professor or TA if they helped:
	* Professor: Scott Vanselow
	* TA: Vladimir Hardy

## History

Information about what has changed.

## Key Programming Concepts Utilized

Key Programming Concepts Utilized in this project:
* Inheritance: a notion by which properties of a subclass are considered the responsibility of a superclass. 
* Encapsulation: software development technique consisting of isolating a system function and provide specifications to a module. 
* Abstraction: a view of an object that focuses information relevant to a purpose and ignores the rest. 
* Polymorphism: allows functions to take many forms allowing the creation of multiple types of objects/classes.

Areas of the project where these concepts were applied:
 * Abstraction was used in the creation of Product class with its implementation of Item Type to hide information meaning total abstraction.
 * Inheritance was applied to 3 classes (Widget, AudioPlayer, and MoviePlayer) these classes were able to interact with its parent class using its super constructor.
 * Polymorphism occurred when using Screen to have multiple MonitorTypes while inheriting from ScreenSpec and the overloading of constructors in ProductionRecord.
 * Encapsulation took place when the overloaded constructor in ProducitonRecord accepted an object of Product to create a unique serial number.

Additional Concepts used:
* ArrayList to store Product and ProductionRecord objects throughout initialize and creation of a product.
* Regular Expression (Regex) to restrict and validate employee name and passwords.
* Recursion to reverse the database password throughout the program for specific access.
