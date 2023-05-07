# final-project-ahsieh10-mma32-pnguye37-szhan257

# Project details
### Project name: 
NYT Sentiment Analysis
### Project description:
Our project intends to examine some of the potential biases that exist in online media. We built a website that takes in a keyword or phrase from the user, fetches articles from the New York Times (NYT) API that are related to the topic, and then runs the leading paragraphs through a text sentiment API, MeaningCloud (MC), in order to see if there is an average difference in sentiment between topics. In addition to displaying the sentiment, the website also ranks and displays the most biased sentences across all articles, gathers the articles' keywords related to the topic, and displays links to the articles analyzed. 
### Team members and contributions 
- ahsieh10 : Worked on backend (NYT API).
- mma32 : Worked on frontend. 
- pnguye37 : Worked on backend (MeaningCloud API).
- szhan257 : Worked on frontend. 
- looked at https://github.com/cs0320-s2023/csv-tnelson.git for fuzz testing

### Total time to complete
3 weeks
### Link to repo
https://github.com/cs0320-s2023/final-project-ahsieh10-mma32-pnguye37-szhan257
# Design choices
In this section, we will talk about the relationships between the multiple classes declared and their functionalities.
### Class Structure (Back End)

1. Interfaces
- CombinedResponse:
Interface that dictates the method of the final response to the front end. This is declared so that the cached version and the manual version of the response fetching have the same properties (strategy pattern).
- MCInter:
Interface that declares the general functionality of the meaning cloud response fetching. This is declared so that the mocked meaning cloud API while testing has the same functionality as the actual API.
- NYTInter:
Interface that declares the general functionality of the New York Times API response. This is declared so that the mocked New York Times API while testing has the same functionality as the actual API.

2. News
- jsonclasses
    - Article:
This is a class that dictates the Article object and specifies which fields from the NYT API response should be kept.
- NYTArticleAPI:
Contains the functionality where, given a keyword, retrieves the NYT API response associated with that keyword and also some extra functionality (like filtering lead paragraphs). Constructor takes in a requestor of type NYTInter (either NYTRequest or NYTMock)
- NYTRequest:
Invokes request functionality of RequestUtil class in Responses package (makes call to NYT API given a keyword)

3. PrivateKey
Contains a class named Keys that stores the keys to NYT and MeaningCloud APIs. This is not pushed to Github for security reasons, the developer can include their own keys on their end.

4. Responses
- utils
    - JSONConverter: Converts arbitrary json string to map (from sprint 4 reflection)
    - RequestUtil: Makes an API request given the URL and returns a Map (taken from Allison’s Sprint 3 code)
- ManualResponse: Makes request to NYT API via the NYTArticleAPI object, retrieves its lead paragraphs, and feeds that into the MCSentimentAPI object. The result of the query is returned to the front end from here. This class also implements the CombinedResponse interface.
- ResponseCache: Contains cache functionality for responses produced by a CombinedResponse object.
- ResponseCreator: Contains functionality for handling erroneous responses and success responses by adding a result field and serializing it into a JSON string.
- SentimentResponse: Declares a SentimentResponse object that dictates the fields kept for the response from the MCSentimentAPI.

5. Sentiment
- jsonclasses
    - PolarityTerm: Declares an object PolarityTerm with a text field.
    - Segment: Contains a list of polarity terms.
    - Sentence: Contains a list of segments and some other metrics returned from the MCSentimentAPI.
    - SentimentJSON: Declares fields associated with the Meaning Cloud API response.
- MCRequest: Makes a request to the Meaning Cloud API.
- MCSentimentAPI: Processes response fetched by MCRequest. Also contains the logic for sorting sentences by bias.
- Score: Class that wraps sentence’s text and score.
- SearchHandler: Contains logic that handles requests to keyword endpoint and produces an API response (this is where information is returned to the front end). 
- Server: Initializes the server and a keyword endpoint associated with the SearchHandler

### Class Structure (Front End)

1. Home Page (Home.tsx)
- Contains consolidated code for the home page part of the program. Has about page popup and query functionalities.

2. Main Functionality (Analyze.tsx)
- Contains consolidated code for the analysis page part of the program, after a user has submitted a query. Has about page popup, query, keyword, sentiment summary, article card, and navbar functionalities.

3. App Functionality (App.tsx)
- Contains the home page and the analyze page, and creates two separate routes for them.

4. Index.tsx
- index.tsx is how we made our commands and functions render on an actual server.

5. Display (Results.tsx, ResultSection.tsx)
- The display functionality for the analysis page of the program. Allows the overall sentiment, key sentences, and keywords to each be displayed as divs containing the respective results.

6. Responsivity (ToggleElement.tsx)
- Enables the analysis page to become responsive for accessibility purposes (zooming in or out on the page will keep the elements relatively intact).

7. Components (Keyword.tsx, Keywords.tsx, AboutPopup.tsx, ArticleCard.tsx, Sidebar.tsx, Navbar.tsx)
- Keyword.tsx sets up keywords within a query as separate clickable buttons that, when clicked, submits a query on behalf of the user for that new keyword.
- Keywords.tsx provides structure for the keywords and lays them out to be displayed in a grid.
- AboutPopup.tsx sets up the about page popup display on both the home & results pages. When the button (displayed on the page) is clicked, a div is overlayed onto the screen with an opaque gray div covering the background.
- ArticleCard.tsx
Sets up the component that shows clickable links to the 10 articles used in the sentiment analysis. The links are button divs displayed within a scrollable container.
- Sidebar.tsx
The scrollable container which is displayed with the 10 articles.

8. Navbar.tsx
- Sets up the navigation bar on the top side of the results page. Has clickable title div component that returns user to the home page, about page pop up button, and user-inputtable input box


### Caching
The cache on the backend stores keywords with their response. The response indicates success vs. error, and either data or an error message. If successful, the data contains a SentimentResponse, which contains the list of articles, the sentiment string, and the list of biased sentences. 


### Errors/Bugs 
None
### Tests
Backend Tests
- TestMCSentimentAPI : This file contains unit tests for the MCSentimentAPI class. These unit tests make sure that given some article text, the class can properly format the url for the MC request, extract relevant information 
from the json response into a SentimentJson obejct, and rank sentences from the article text after analyzing sentiment.
- TestNYTArticleAPI: This file contains unit tests for the NYTArticleAPI class. These unit tests make sure that the class can properly retrive articles from the API json response, handle errors when given a bad search term, and filter articles into a list of strings to be used for sentiment analysis.
- TestResponseCache: This file contains unit tests for the ResponseCache class. It tests that the cache can properly store sentiment responses with the search term, that identical search terms are treated the same, and that the time and size limits restrict the cache storage.
- TestSearchHandler: This file contains integration tests that test the entire pipeline. It uses mock requesters for both the NYT and MC, so that we don't need to make excessive calls to both APIs but can work with pre-defined jsons. It tests that when the SearchHandler is given a search term, it can properly get a successful response with the correct sentiment, list of ranked sentences, and list of articles. It also tests that muliple searches work, and improper and random searches won't crash the server (fuzz testing).

Frontend Tests
- App.test.tsx: Tests that the home page of the app is displaying properly with the correct components. Also tests that the about page button pop up on the homepage is working correctly. Uses aria labels to track components.
- Analyze.test.tsx: Tests that the analyze page of the app is displaying properly with the correct components. Unit tests check for valid user inputs, invalid user inputs, and sequential valid/invalid user inputs. Integration testing checks that a user is able to successfully return to the home page after querying a valid input.


### How to run tests
To run the tests on the backend, go to the backend directory ('cd back') and run the command 'mvn test' or run the appropriate test files. We have four test files: TestMCSentimentAPI, TestNYTArticleAPI, TestResponseCache, and TestSearchHandler. To run the tests on the frontend, go into the frontend directory ('cd front') and run 'npm test.' 
### How to run the program
To get the server running, run the Server file in the backend. 
In the front end folder ('cd front'), run 'npm install' to ensure all dependencies are downloaded. Run 'npm start' and follow the link to get to the website. 
### How to use the website
The homepage is a welcome page. To get started, type in the searchbar a search term(s) representing the topic to analyze. In the new page, there will be a sidebar on the leftside with links to the NYT articles that the user can redirect to if they want to read it. Pressing any of the keywords at the bottom will prompt a new sentiment analysis using that keyword. Clicking on the tabs at the top will display a specific component of the sentiment analysis (sentiment, ranked sentences, or keywords). Click on the i button for a disclaimer and overview information about the website.
### Accessibility shortcuts
1. Ctrl F : Focus input
2. Enter : Submit search query
3. Left / Right arrow keys : Navigate between result sections 
4. A : Get to the about page
Queries can be made through the url search parameters as well.