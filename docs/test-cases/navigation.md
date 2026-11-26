# Test Cases: Home Page, Navigation and Static Pages (FT-01, FT-11)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Features | FT-01 - Home page and navigation; FT-11 - Static pages (Test Cases, API list, unknown URL handling) |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- **Auto** shows whether the case is a candidate for Selenium automation.
- The public site can change at any time. The tests compare relationships (for example, the number of products on the home page equals the number on the Products page) and read values at run time.
- The pages `Test Cases` and `API list` are read-only pages that explain practice material. They have no business function, so they get one light smoke test at most (see TC-NAV-012 and TC-NAV-013).
- Each case starts with the user logged out on the home page `https://automationexercise.com/` unless the case says otherwise. The header of a logged-in user has other items (**Logout**, **Delete Account**, `Logged in as ...`).
- Advertising can add underlined words, small tags and side panels to pages. The tests ignore text added by advertising.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Header item | Page opened |
|---|---|
| Home | `https://automationexercise.com/` |
| Products | `https://automationexercise.com/products` |
| Cart | `https://automationexercise.com/view_cart` |
| Signup / Login | `https://automationexercise.com/login` |
| Test Cases | `https://automationexercise.com/test_cases` |
| API Testing | `https://automationexercise.com/api_list` |
| Contact us | `https://automationexercise.com/contact_us` |
| Video Tutorials | `https://www.youtube.com/c/AutomationExercise` (another site) |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-NAV-001 | Each header item opens the right page (data-driven) | Positive | High | Yes | The home page is open | 1. Click an item of the "Header item" data set (except **Video Tutorials**).<br>2. Read the URL.<br>3. Go back to the home page and repeat for each item. | Each item opens the page of the data set in the same tab. | FT-01 |
| TC-NAV-002 | Video Tutorials opens the YouTube channel in the same tab | Positive | Low | No | The home page is open | 1. Click **Video Tutorials** in the header. | The YouTube channel `https://www.youtube.com/c/AutomationExercise` is opened in the same tab. This case is checked by hand because it leaves the site under test. | FT-01 |
| TC-NAV-003 | The header is the same on every page and the item of the open page is highlighted | Positive | Low | Yes | The home page is open | 1. Open `Products`, `Cart`, `Contact us` and `Test Cases` one by one.<br>2. Look at the header on each page. | The header has the same items on each page. The item of the open page is highlighted in orange. | FT-01 |
| TC-NAV-004 | The logo opens the home page | Positive | Low | Yes | The Products page is open | 1. Click the Automation Exercise logo. | The home page `https://automationexercise.com/` is displayed. | FT-01 |
| TC-NAV-005 | The slider arrows and dots change the slide | Positive | Low | Yes | The home page is open | 1. Click the right arrow, then the left arrow.<br>2. Click each of the dots. | The arrows show the next or the previous slide. Each dot moves to its own slide. The slider has 3 slides. | FT-01 |
| TC-NAV-006 | The slider changes slide by itself | Positive | Low | No | The home page is open | 1. Wait without touching the slider. | The slider changes to the next slide about every 5 seconds. This case is checked by hand because it depends on time. | FT-01 |
| TC-NAV-007 | The slider buttons open the Test Cases and API list pages | Positive | Low | Yes | The home page is open on a slide with the buttons | 1. Click **Test Cases** on the slider.<br>2. Go back to the home page and click **APIs list for practice**. | The first button opens `https://automationexercise.com/test_cases` and the second opens `https://automationexercise.com/api_list`. Both open in the same tab. | FT-01 |
| TC-NAV-008 | The scroll-up arrow appears after scrolling down and moves to the top | Positive | Low | Yes | The home page is open at the top | 1. Look for the orange arrow at the bottom right.<br>2. Scroll down.<br>3. Click the arrow. | At the top of the page the arrow is not shown. After scrolling down it is shown. After the click the page scrolls to the top. | FT-01 |
| TC-NAV-009 | Features Items shows all products without paging | Positive | Low | Yes | The home page is open | 1. Count the products in **Features Items**.<br>2. Look for a **load more** button or page numbers.<br>3. Open the Products page and count the products. | The number of products on the home page equals the number on the Products page (34 when seen during exploration). There is no **load more** button and no page numbers. | FT-01 |
| TC-NAV-010 | The Recommended items arrows change the products | Positive | Low | Yes | The home page is open and scrolled to **Recommended items** | 1. Note the three products shown.<br>2. Click the arrow button. | Three other products are shown. The area has 2 slides with 3 products each. | FT-01 |
| TC-NAV-011 | The footer has only the subscription area and the copyright line | Observation | Low | No | A page of the site is open and scrolled to the bottom | 1. Look at the footer on the home page, Products, Login, Contact us and Cart. | The footer has the subscription area and the line `Copyright © 2021 All rights reserved`. No links, social media or contact information are shown. No requirement is available to confirm whether this is intended. | FT-01 |
| TC-NAV-012 | The Test Cases page lists test cases and shows the steps of one | Positive | Low | Yes | None | 1. Open `https://automationexercise.com/test_cases`.<br>2. Click one test case in the list. | The page shows a list of test cases. After the click the steps of that test case are displayed. The test does not check the number or the names of the test cases. | FT-11 |
| TC-NAV-013 | The API list page shows the details of one API | Positive | Low | No | None | 1. Open `https://automationexercise.com/api_list`.<br>2. Click one API in the list. | The page shows a list of APIs. After the click the API URL, the request method, the response code and the response JSON are displayed. This case is checked by hand because the page is documentation without a business function. | FT-11 |
| TC-NAV-014 | An address that does not exist opens the home page | Observation | Low | No | None | 1. Type `https://automationexercise.com/abc123` in the address bar and press Enter. | The user is taken to the home page `https://automationexercise.com/`. There is no error page. No requirement is available to confirm whether this is intended. | FT-11 |
