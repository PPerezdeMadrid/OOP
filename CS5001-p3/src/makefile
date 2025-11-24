SRC_DIR=.
TEST_DIR=test
BIN_DIR=bin
DOC_DIR=doc

SOURCES=$(shell find $(SRC_DIR) -name "*.java" ! -path "./test/*")
TEST_SOURCES=$(shell find $(TEST_DIR) -name "*.java")

STANDALONE_JAR = JUnit/lib-alone/junit-platform-console-standalone-1.13.4.jar

CLASSPATH=$(BIN_DIR):$(STANDALONE_JAR)

.PHONY: all compile run compile_tests test javadoc clean

all: test

compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

compile_tests: compile
	javac -cp "$(CLASSPATH)" -d $(BIN_DIR) $(TEST_SOURCES)

test: compile_tests
	@echo "Running JUnit tests..."
	java -jar $(STANDALONE_JAR) -cp $(BIN_DIR) --scan-classpath

run: compile
	java -cp $(BIN_DIR) view.MandelbrotApp

javadoc:
	mkdir -p $(DOC_DIR)
	javadoc -d $(DOC_DIR) -sourcepath $(SRC_DIR) $(SOURCES)

clean:
	rm -rf $(BIN_DIR) $(DOC_DIR)
