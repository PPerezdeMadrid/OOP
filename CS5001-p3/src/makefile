
SRC_DIR=src
TEST_DIR=test
BIN_DIR=bin

SOURCES=$(shell find $(SRC_DIR) -name "*.java")
TEST_SOURCES=$(shell find $(TEST_DIR) -name "*.java")

CLASSPATH=$(BIN_DIR)

.PHONY: all compile run compile_tests test clean

all: run

compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)


run: compile
	java -cp $(CLASSPATH) view.MandelbrotApp


compile_tests: compile
	javac -d $(BIN_DIR) -cp $(CLASSPATH) $(TEST_SOURCES)

test: compile_tests
	java -cp $(CLASSPATH) ModelMandelbrotTest

clean:
	rm -rf $(BIN_DIR)
