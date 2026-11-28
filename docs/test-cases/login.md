# Test Cases: Login and Logout (FT-07)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-07 - Login and logout |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-28 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- **Auto** shows whether the case is a candidate for Selenium automation.
- Messages for empty required fields are browser validation bubbles, not messages of the website. Their text depends on the browser language, so the tests check that a bubble appears on the right field and that the form is not submitted, not the text.
- Credentials are never stored in this repository. Before each case that needs an account, the test creates a new one at run time (a unique `...@example.com` email and a password generated at run time, fake data only) and deletes it afterwards. `<TEST_EMAIL>` and `<TEST_PASSWORD>` in the cases stand for these run-time values.
- Each case starts from a logged-out state with an empty cart.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Email | Password |
|---|---|---|
| Valid user | `<TEST_EMAIL>` | `<TEST_PASSWORD>` |
| Wrong password | `<TEST_EMAIL>` | `<WRONG_PASSWORD>` (any value that is not the real password) |
| Unregistered email | `<UNREGISTERED_EMAIL>` (an address that was never registered, for example `nobody-<timestamp>@example.com`) | `<TEST_PASSWORD>` |
| Uppercase email | `<TEST_EMAIL>` written in uppercase letters | `<TEST_PASSWORD>` |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-LOGIN-001 | Login succeeds with the correct email and password | Positive | High | Yes | The login page (`/login`) is open and the user is not logged in | 1. In the login section, enter `<TEST_EMAIL>`.<br>2. Enter `<TEST_PASSWORD>`.<br>3. Click **Login**. | The user is logged in and the home page `https://automationexercise.com/` is displayed. The header shows **Logout**, **Delete Account** and `Logged in as` followed by the name of the account. | FT-07 |
| TC-LOGIN-002 | Login fails with a wrong password | Negative | High | Yes | The login page is open and the user is not logged in | 1. Enter `<TEST_EMAIL>`.<br>2. Enter the wrong password.<br>3. Click **Login**. | The error message `Your email or password is incorrect!` is displayed in red below the Password field. The user is not logged in. | FT-07 |
| TC-LOGIN-003 | Login fails with an email that is not registered | Negative | High | Yes | The login page is open and the user is not logged in | 1. Enter the unregistered email.<br>2. Enter `<TEST_PASSWORD>`.<br>3. Click **Login**. | The error message `Your email or password is incorrect!` is displayed in red below the Password field, the same message as for a wrong password (see TC-LOGIN-002). The user is not logged in. | FT-07 |
| TC-LOGIN-004 | Login is blocked when the email or the password is empty (data-driven) | Negative | Medium | Yes | The login page is open and the user is not logged in | 1. Leave the email empty and enter `<TEST_PASSWORD>`, then click **Login**.<br>2. Reload the page, enter `<TEST_EMAIL>` and leave the password empty, then click **Login**.<br>3. Reload the page, leave both fields empty, then click **Login**. | In each case a browser validation bubble appears on an empty field. The form is not submitted and the user is not logged in. | FT-07 |
| TC-LOGIN-005 | Logout returns to the login page | Positive | High | Yes | The user is logged in | 1. Click **Logout** in the header. | The login page `https://automationexercise.com/login` is displayed. The header shows **Signup / Login** and no longer shows **Logout** or `Logged in as`. | FT-07 |
| TC-LOGIN-006 | Products added before login stay in the cart after login | Positive | High | Yes | The user is logged out, the cart is empty, and the account in `<TEST_EMAIL>` exists | 1. Add a product to the cart (for example `Blue Top`).<br>2. Open the login page and log in with `<TEST_EMAIL>` and `<TEST_PASSWORD>`.<br>3. Open `/view_cart`. | The product added in step 1 is still in the cart. | FT-07, FT-05 |
| TC-LOGIN-007 | Login with the email in uppercase is rejected | Observation | Low | No | The login page is open and the user is not logged in | 1. Enter `<TEST_EMAIL>` written in uppercase letters.<br>2. Enter `<TEST_PASSWORD>`.<br>3. Click **Login**. | The error message `Your email or password is incorrect!` is displayed in red below the Password field and the user is not logged in. No requirement is available to confirm whether the email must be case-sensitive. | FT-07 |
| TC-LOGIN-008 | A logged-in user who opens the login page is sent to the home page | Observation | Low | No | The user is logged in | 1. Type `https://automationexercise.com/login` in the address bar and press Enter. | The login page is not shown. The user is taken to the home page `https://automationexercise.com/`. No requirement is available to confirm whether this is intended. | FT-07 |
