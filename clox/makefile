CC := gcc
CFLAGS := -g -Wall

# globs
SRCS := $(wildcard src/*.c)
HDRS := $(wildcard src/*.h)
OBJS := $(patsubst src/%.c,out/%.o,$(SRCS))

TARGET := clox

$(TARGET): $(OBJS) $(HDRS) makefile
		@if not exist out mkdir out
		$(CC) $(CFLAGS) $(OBJS) -o $(TARGET)

# compile an object based on source and headers
out/%.o: src/%.c $(HDRS) makefile
		@if not exist out mkdir out
		$(CC) $(CFLAGS) -c $< -o $@

# tidy up
clean:
		@rmdir /s /q "./out"


