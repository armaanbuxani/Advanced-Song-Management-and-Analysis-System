runBDTests: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runApp: App.java
	javac -cp .:../junit5.jar IterableRedBlackTree.java
	javac App.java
	java App

Frontend.class:	Frontend.java
	javac Frontend.java
FrontendDeveloperTests.class: FrontendDeveloperTests.java Frontend.class
	javac -cp .:../junit5.jar FrontendDeveloperTests.java
runFDTests: FrontendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
clean:
	rm -f *.class
