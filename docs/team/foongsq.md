---
layout: page
title: Foong Siqi's Project Portfolio Page
---

## Project: FlashNotes

FlashNotes is a desktop flashcards application used for creating and accessing flashcards for studying. The user 
interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 12 kLoC.

Given below are my contributions to the project.

* **New Feature 1**: Setting up of review mode (Pull request: [#92](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/92))
    * What it does: This feature processes the list of flashcards to be reviewed when the user calls the `review` command.
    It then passes this list of flashcards to the UI component and opens a new JavaFX window for users to review their
    flashcards.
    * Justification: This is a very important feature as it forms the foundation of one of the main functions of FlashNotes
    which is for users to review their flashcards. Also it is one of the aspects of the application that users interact with
    the most, so it is a significant feature.
    * Highlights: This enhancement required the in-depth analysis of the UI and Model component. The implementation was
    also challenging as it involved using many methods/functions from JavaFX which is a relatively new framework for me.

* **New Feature 2**: Implementation of review limit (Pull request: [#168](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/168))
    * What it does: Saving and retrieving of a maximum number of cards that the user wants to review.
    * Justification: This feature is important as it gives users flexibility and a choice to set preferences. This is the
    first of many other potential settings we can let users customize, for them to personalize their flashcard making
    and reviewing experience.
    * Highlights: I implemented the review limit in such a way that it can accept a range of integer inputs from 1 to 
    `Integer.MAX_VALUE` and also valid string inputs such as `all`. 

* **New Feature 3**: Implementation of character limit for question, answer and deck names (Pull request: [#172](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/172))
    * What it does: Limits the number of characters that users can input for question, answer and deck names to 140 
    characters.
    * Justification: This feature is important as it affects some of the UI decisions we made. Some examples include
    how to handle overflow in the content we want to display, and the sizing of the different windows.

<div style="page-break-after: always;"></div>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=foongsq&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
    * Used Github Issues Tracker to manage and assign tasks to team mates.
    * Facilitated weekly team meetings about implementation details, deliverables,
    and deadlines.
    * Brainstorm ideas for the different features (How we intend our application to behave, design details etc.)

* **Enhancements to existing features**:
    * Refactored code for `Name` class in the original AddressBook3 application
    into the `Question` class for FlashNotes application.
    * Standardized and updated some of the error messages to improve user experience.
    * Organized code and fixed bugs in Parsers, to ensure that the correct commands are allowed in the correct modes.
    * Wrote new testcases for major Parser class `FlashNotesParser`, and also for the new commands I implemented. 
    (Pull request: [#138](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/138))

* **Documentation**:
  * User Guide:
    * Wrote documentation for the features `setReviewLimit`, `checkReviewLimit`, `review`.
    * Updated documentation for `find`, `deleteCard`.
    * Wrote documentation for the section "Understanding the 3 different modes".
    * Updated "Manual Testing" section with new features.
    * Standardized formatting and fixed typos that could cause confusion for readers.
  * Developer Guide:
    * Add design considerations and explanation for the 3 different UIs available in FlashNotes v1.4.
    * Update documentation in "Architecture" section.
    * Documented effort and challenges faced when implenting the 3 UI modes
    * Wrote documentation for implementation of Review mode.
    * Drew sequence diagram for `review` command execution, activity diagram for `setReviewLimit` command execution, 
    class diagram for UI and `CommandResult` classes, and activity diagram for a typical workflow in the review mode.
    * Standardized formatting and fixed typos that could cause confusion for readers.

* **Community**:
    * Reviewed team mates' pull requests (Pull Request: [#57](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/57))
