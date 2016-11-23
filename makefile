MAIN=CSVEditor
CLASSPATH=".:./txtlib.jar:./opencsv-3.8.jar"

run: compile
	java -cp ${CLASSPATH} ${MAIN}
compile: CSVEditor.class
CSVEditor.class: CSVEditor.java
	javac -cp ${CLASSPATH} ${MAIN}.java

clean:
	-rm -f *.class *~
