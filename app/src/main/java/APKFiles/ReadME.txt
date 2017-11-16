WARNING: Before placing the newest apk verify that it works and does not cause crashing. We should only place copies of the apk that are from working builds. So far the best way to verify is to check
with either Miles or Roger that the apk works on their phones. We don't want to place a copy of the apk if we know it is broken or unsure if it is broken in times when we need the apk itself.

When you add an apk, include the data in here, and also include the date of the apk you are adding, I think we should keep a series of apk's in this directory so that we can go back to older models if
need be.

Miles' Game Hack Steps:
        /* Case 1 - Running a new game locally
        1. Create the new game passing your current username and a registered username (both 'white' and 'black' are registered for this purpose
            String newGameKey = driver.createGame(your_username, other_reg_username);
        2. Set returned key as the drivers current game key
            driver.setCurrentGameKey(newGameKey);
        3. Create the board ui
            boardUI = (BoardUI) findViewById(R.id.chessboard);
        4. Register the board UI to the new game
            boardUI.registerToSnapshot(newGameKey);
        5. go to MovePieceActionListener::93 and ensure the code is set for LOCAL
         */

        /* Case 2 - Running a saved game locally
        1. To do this you must copy the game key value for a previous Case1. Run the Case 1 save the key then exit and setup for Case 2
        2. Set the driver to the saved game key (it should look something like '-KyrHJc4s6basDQ7qcor')
            driver.setCurrentGameKey(savedGameKey);
        3. Create the board ui
            boardUI = (BoardUI) findViewById(R.id.chessboard);
        4. Register the board UI to the saved game
            boardUI.registerToSnapshot(savedGameKey);
        5. go to MovePieceActionListener::93 and ensure the code is set for LOCAL
         */

        /* Case 3 - Running a shared game between users
         1. Run a Case 1 passing your username as normal and the other users username as the second arg. Save the game key and exit
         2. Set the driver to the saved game key (it should look something like '-KyrHJc4s6basDQ7qcor')
            driver.setCurrentGameKey(savedGameKey);
         3. Create the board ui
            boardUI = (BoardUI) findViewById(R.id.chessboard);
         4. Register the board UI to the saved game
            boardUI.registerToSnapshot(savedGameKey);
         5. go to MovePieceActionListener::93 and ensure the code is set for REMOTE
         */


Apk upload dates:
1.) 10/27/2017
2.) 11/05/2017
3.) 11/15/2017