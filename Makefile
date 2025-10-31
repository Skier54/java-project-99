.DEFAULT_GOAL := build-run

clean:
	@./gradlew clean

build:
	@./gradlew clean build

install:
	@./gradlew clean installDist

run-dist:
	@./build/install/bin/app

run:
	@./gradlew run

test:
	@./gradlew test

report:
	@./gradlew jacocoTestReport

check:
	@./gradlew checkstyleMain
	@./gradlew checkstyleTest

update-deps:
	@./gradlew refreshVersions
	# @./gradlew dependencyUpdates -Drevision=release

build-run: build run

.PHONY: build