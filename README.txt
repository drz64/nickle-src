BUILD:
	./gradlew clean build


COMPILE:
	./gradlew run -q --args ./example.iloc > example.c
	 gcc -I src/main/c example.c src/main/c/*.c


RUN:
	./a.out foo bar


GIT (First time): 
	git init
	git add .
	git commit -m "initial commit"
	git branch -M main
	git remote add origin https://github.com/drz64/<repo>.git
	git push -u origin main

