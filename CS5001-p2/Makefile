# === Makefile for CS5001 OOP Coursework ===

# Paths
SRC_DIR = src
BIN_DIR = bin
LIB_DIR = Test/lib
STANDALONE_JAR = Test/lib-alone/junit-platform-console-standalone-1.13.4.jar

# Find all .java files inside src
SOURCES := $(shell find $(SRC_DIR) -name "*.java")

# === Default target ===
all: compile

# === Compile all Java files ===
compile:
	@javac -cp "$(STANDALONE_JAR):$(LIB_DIR)/*" -d $(BIN_DIR) $(SOURCES)
	@echo "Compilation finished."

# === Run all JUnit tests ===
test: compile
	@echo "Running all JUnit 5 tests..."
	@java -jar $(STANDALONE_JAR) -cp $(BIN_DIR) --scan-classpath

# === Clean compiled files ===
clean:
	@rm -rf $(BIN_DIR)/*
	@echo "Clean complete."
