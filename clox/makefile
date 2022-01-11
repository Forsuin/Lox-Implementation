BUILD_DIR := build
CC = gcc

default: debug

clean:
	@ rm -rf $(BUILD_DIR)
	@ rm -rf clox
	@ rm -rf cloxd

# Compile a debug build of clox.
debug:
	@ $(MAKE) -f util/c.make NAME=cloxd MODE=debug SOURCE_DIR=src

# Compile the C interpreter.
clox:
	@ $(MAKE) -f util/c.make NAME=clox MODE=release SOURCE_DIR=src
	@ cp build/clox clox # For convenience, copy the interpreter to the top level.