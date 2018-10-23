all: 
	javac -cp json-20180813.jar:.  UTECECourses.java
run: all
	java -cp json-20180813.jar:. UTECECourses
