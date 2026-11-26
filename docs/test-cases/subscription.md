# Test Cases: Newsletter Subscription (FT-10)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-10 - Newsletter subscription (footer) |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- **Auto** shows whether the case is a candidate for Selenium automation.
- Messages for an empty field and for a badly formatted email are browser validation bubbles, not messages of the website. The tests check that a bubble appears on the field and that the form is not submitted, not the text.
- The success message disappears after about 5 seconds, so the tests wait for the message to appear and read it at once.
- The subscription area is in the page footer. The user can be logged out. Each case starts on the home page `https://automationexercise.com/` unless the case says otherwise.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Value |
|---|---|
| Valid email | `test@example.com` |
| Invalid emails | `test`, `test@` |
| Pages with the subscription area | Home, Products, Cart, Login, Contact us, a product details page (for example `/product_details/1`), Test Cases, API Testing |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-SUB-001 | Subscribe is blocked when the email is empty | Negative | Medium | Yes | The subscription area in the footer is visible | 1. Leave the email field empty.<br>2. Click the arrow button next to the field. | A browser validation bubble appears on the email field (`Your email address`). The form is not submitted. | FT-10 |
| TC-SUB-002 | Subscribe is blocked for an email in an invalid format (data-driven) | Negative | Medium | Yes | The subscription area in the footer is visible | 1. Enter a value from the "Invalid emails" data set.<br>2. Click the arrow button.<br>3. Repeat for each value. | A browser validation bubble appears on the email field. The form is not submitted. | FT-10 |
| TC-SUB-003 | Subscribe with a valid email shows a success message | Positive | Medium | Yes | The subscription area in the footer is visible | 1. Enter `test@example.com`.<br>2. Click the arrow button.<br>3. Wait about 5 seconds. | The message `You have been successfully subscribed!` is displayed in a green box above the `SUBSCRIPTION` title. While the message is displayed, the email field still shows the entered email in light grey text. After about 5 seconds the message disappears and the email field is empty. | FT-10 |
| TC-SUB-004 | The same email can be subscribed again | Observation | Low | No | The email `test@example.com` was already subscribed (see TC-SUB-003) | 1. Enter `test@example.com` again.<br>2. Click the arrow button. | The subscription works again and shows the same success message. No message about a duplicate is displayed. No requirement is available to confirm whether this is intended. | FT-10 |
| TC-SUB-005 | The subscription area is on every page of the site (data-driven) | Positive | Low | Yes | None | 1. Open a page from the "Pages with the subscription area" data set.<br>2. Scroll to the bottom of the page.<br>3. Repeat for each page. | The subscription area (a title `SUBSCRIPTION`, an email field and an arrow button) is shown in the footer of each page. The **Video Tutorials** link opens YouTube, which is a different site, and is not part of this check. | FT-10 |
