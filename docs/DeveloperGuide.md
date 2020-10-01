---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* NUS FASS student who likes to write down lecture notes
* practices active learning techniques via flash cards
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage and create flashcards faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new card                 |                                                                        |
| `* * *`  | user                                       | delete a card                  | remove entries that I no longer need                                   |
| `* * *`  | user                                       | find a card by keyword         | locate certain cards without having to go through the entire list      |
| `* *`    | user                                       | hide old cards                 | clear clutter when there are too many cards in the deck                |
| `*`      | user with many related cards in the app    | nest the card decks by tags    | locate a cards of the same group easily when reviewing                               |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `FlashNotes` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a card**

**MSS**

1.  User requests to list cards
2.  Flashnotes shows a list of cards
3.  User requests to delete a specific card in the list
4.  Flashnotes deletes the card

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Flashnotes shows an error message.

      Use case resumes at step 2.

**Use case: Add a card**

**MSS**

1.  User add card
2.  Flashnotes adds the card
    Use case ends.

**Extensions**

* 2a. There is a duplicate question.
    
    * 2a1. Flashnotes shows an error message.

      Use case resumes at step 2.

**Use case: Edit a card**

**MSS**

1.  User requests to list cards
2.  Flashnotes shows a list of cards
3.  User requests to edit a specific card in the list
4.  Flashnotes edits the card

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Flashnotes shows an error message.

      Use case resumes at step 2.
      
      
**Use case: Filtering cards by a certain tag**

**MSS**
1. User filters all cards using a certain tag.
2. Flashnotes shows a list of cards that contain the tag.

Use case ends.

**Extensions**

* 1a. User enters a tag that is not found

    * 1a1. Flashnotes returns no cards.

    Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 cards without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Searching for a flashcard should not take more than 2 seconds.
5.  The user can directly edit the data file to add or edit flashcards.
6.  The user can import or export the flashcards by adding/copying a new json file with the same name.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Flashcard**: A card with a question and answer, and may contain a tag.
* **Flashnotes**: The software that stores flashcards.
* **Tag**: A note to group cards of a certain category together.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample flashcards. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a card

1. Deleting a card while all persons are being shown

   1. Prerequisites: List all cards using the `list` command. Multiple cards in the list.

   1. Test case: `delete 1`<br>
      Expected: First card is deleted from the list. Details of the deleted card shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No card is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_


### Finding cards by tag
1. Finding cards via a specific tag

    1. Prerequisites: Flashnotes have cards containing tag 'Singapore' and 'Malaysia'
    
    1. Test case: `listTags Singapore` <br>
       Expected: All cards with the tag 'Singapore' will be shown. Cards with tags 'Malaysia' will not be shown.
       
    1. Test case: `listTags singapore` <br>
       Expected: None of the cards are shown (as the keyword is case sensitive)
       
    1. Test case: `listTags Singapore Malaysia` <br>
       Expected: All cards with the tag 'Singapore' and cards with the tag 'Malaysia' will be shown.


1. _{ more test cases …​ }_
test
