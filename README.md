# java-search

The goal of the project is to use threads in java to perform multiple search over text file.

## Structure of the code

The code contains:
	- Main file
	- Matcher
	- Aggregator

### Matcher

The matcher unherits from Thread.
Given a list of words and a text, the matcher will return all occurrences of each words (line offset and char offset) in the text.

### Aggregator

The aggregator is responsible for combining the results of each matcher and display it to the user.

 

