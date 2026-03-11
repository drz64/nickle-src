BUILD:
	./gradlew clean build


COMPILE:
	./gradlew run -q --args ./example.iloc > example.c
	 gcc -I src/main/c example.c src/main/c/*.c


RUN:
	./a.out foo bar
