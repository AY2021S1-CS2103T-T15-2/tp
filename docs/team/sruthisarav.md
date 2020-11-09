---
layout: page
title: Sruthi's Project Portfolio Page
---

## Project: FlashNotes

FlashNotes is a desktop address book application used for creating and accessing flashcards for studying. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 12 kLoC.

Given below are my contributions to the project.

* **New Feature 1**: Flipping of flashcard in Review Mode
    * What it does: This feature flips the flashcard currently being reviewed when the user calls the `f` command.
    This will switch between question and answer of the flashcard currently being reviewed.
    * Justification: This is an important feature in the review mode as when the user is reviewing a deck of flashcards, 
    they wouldn't want to be able to view the answer immediately. This feature ensures that the user can view either 
    the question or answer at any point in time and not both. Also, every flashcard needs to be flipped in order 
    to be able to proceed to the next flashcard to review in the review mode.
    * Highlights: This enhancement required an in-depth analysis of the Logic and Model components. The implementation 
    was also challenging as it involved interactions between several components(UI, Logic and Model) in order to make 
    the feature work.
    Another highlight would be hiding the view of either the question/answer whenever the flashcard is flipped so that 
    the user doesn't accidentally view the answer to the question when they are not supposed to be able to.
 
* **New Feature 2**: Marking flashcard as correct in Review Mode
    * What it does: Marks the flashcard currently being reviewed in the Review Mode as correct and shows the next 
    card in the review list.
    * Justification: This feature is important as it enables users to keep track of the flashcards that they get right 
    during the review session, allowing them to move on to the next flashcard.
    * Highlights: I added a progress bar at the bottom of the review mode window so that users can note their 
    progress during a review session. Whenever a flashcard is marked as correct, the users can see the progress 
    increase until it reaches full 100% which means that the user has completed reviewing the list of flashcards for 
    that particular review session.  
    
<div style="page-break-after: always;"></div>
* **New Feature 3**: Marking flashcard as wrong in Review Mode
    * What it does: Marks the flashcard currently being reviewed in the Review Mode as wrong and shows the next 
    card in the review list.
    * Justification: This feature is important as it enables users to keep track of the flashcards that they get wrong 
    during the review session, allowing them to move on to the next flashcard. For every flashcard that the user 
    reviews incorrectly, the flashcard will be added back to the list so that the user reviews it again within the same 
    review session before they forget about it. 
    * Highlights: I added a progress bar at the bottom of the review mode window so that users can 
    note their progress during a review session. Whenever a flashcard is marked as wrong, the progress in the bar will
    not change at all, showing the user that they still have just as many cards to review as they did before this 
    flashcard. This is because the user has to review the same flashcard that they had gotten wrong, again, before 
    they forget about the question. 

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=foongsq&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
    * Merged PRs for v1.3 documentation and double-checked everyone's documentation.
    * Attended weekly team meetings about implementation details, deliverables, and deadlines and shared my 
    inputs/opinions/suggestions.
    * Brainstorm ideas for the different features.
 
* **Enhancements to existing features**:
    * Refactored code for `Phone` class in the original AddressBook3 application
    into the `Answer` class for FlashNotes application.
    * Updated old testcases for `Phone` and changed them to `Answer`.
    * Created and updated most of the error messages to improve user experience.
    * Organized code and fixed bugs in Parsers, to ensure that the incorrect commands aren't executed, but return 
    error messages instead.
    * Created three new parser functions inside FlashNotesParser to parse incorrect commands in the respective modes 
    such that they return appropriate error messages. 

<div style="page-break-after: always;"></div>
* **Documentation**:
  * User Guide:
    * Wrote documentation for the commands: `f`, `c`, `w`.
    * Updated the documentation for `editCard`.
    * Wrote documentation for the first half of section "Understanding the 3 different modes" and included annotations 
    for Main Mode UI.
    * Updated command section for review mode.
    * Wrote the entirety of the FAQ section.
  * Developer Guide:
    * Update documentation in "Logic" section.
    * Update Delete Sequence UML diagram and Logic Class UML diagram.
    * Wrote documentation for implementation of the features: flipping flashcard in review mode and marking flashcards 
    as correct or wrong before proceeding to review the next flashcard.
    * Drew a sequence diagram for `f` command execution, and an activity diagram for `c` or `w` command execution in 
    the review mode.
    * Wrote the introduction, which includes target audience, purpose and brief summary of all the sections in the 
    developer's Guide.
    * Updated Manual Testing section to include all the new features in FlashNotes.    

* **Community**:
    * Reviewed teammates' pull requests. [link](https://github.com/AY2021S1-CS2103T-T15-2/tp/pull/184)
