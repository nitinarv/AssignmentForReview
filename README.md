# AssignmentForReview

The assignment requires that I hit a webpage and display the following

1. Get the first 10th character in the page. 
2. Get ever 10th character that occurs in the page and put it into a data structure of some kind. Like an ArrayList for Character .
3. Split the contents of the entire page on the basis of whitespaces and count their occurences. And hold that information in a data structure of somekind, Like a HashMap for a key value of String and Integer.
4. And be able to search for a string among the different words we have and tell the user the number of times it has been repeated.

That is the basic gist of what was expected. 

The following are the details of what other things were implemented.

1. Using v7 appcompat library NavigationDrawer
2. Using v7 appcompat related RecyclerView Library.
3. Using toolbar from the v7 appcompat library
4. Using AppCompatActivity instead of ActionBarActivity
5. Added gester detection for sliding in and out the Navigation Drawer from the Left side of the screen.
6. Added activation of the Navigation drawer from clicking the drawer Icon on the top left corner of the toolbar.
7. Added progressbar to display the progress of the tasks involved.
8. Wrote a extended class of the AsyncTask class to do the 'getting' of the page and then processing of the string on the basis of what we want.
9. Wrote a callback that can be used for all the different types of use we have for the task.
10. In the NavigationDrawer on click of the list items we scroll down automatically to that result on the main screen.
11. Implmented searchview in the Toolbar. Implemented a workaround to make it show the suggestions from the list
12. Wrote a class to hold the response. I will have to add another pojo for handling holding the rest response and process it.

