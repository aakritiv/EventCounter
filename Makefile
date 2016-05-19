JavaCompiler = javac 

.SUFFIXES: .java .class

.java.class:
	$(JavaCompiler) $*.java

CLASSES=  \
	redBlackTree/Node.java\
	redBlackTree/RedBlackTree.java\
	Counter.java\
	bbst.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class
	$(RM) redBlackTree/*.class

