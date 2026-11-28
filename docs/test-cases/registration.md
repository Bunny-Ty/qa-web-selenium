# Test Cases: Account Registration and Deletion (FT-06)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-06 - Account registration and deletion |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-28 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- Cases with **Auto** = `No (Defect candidate)` show a reasonable expected result and the behavior that was observed. They are possible bugs that are not confirmed yet (see the test plan, Section 5.4). They are not automated until the behavior is confirmed.
- **Auto** shows whether the case is a candidate for Selenium automation.
- Messages for empty required fields and for badly formatted emails are browser validation bubbles, not messages of the website. Their text depends on the browser language, so the tests check that a bubble appears on the right field and that the form is not submitted, not the text.
- Credentials are never stored in this repository. A case that needs an account creates a new one at run time (a unique `...@example.com` email and a password generated at run time, fake data only), and the test deletes it afterwards. `<TEST_EMAIL>` and `<TEST_PASSWORD>` in the cases stand for these run-time values..
- Cases that create an account must delete it at the end (see TC-REG-011). The cart belongs to the account, so deleting the account also removes its cart.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Value |
|---|---|
| Name | `Test User` |
| Unique email | `qa-<timestamp>@example.com`, generated at run time (every run uses a new email) |
| Existing email | `<TEST_EMAIL>` (the email of an account created by the test before the case starts) |
| Password | `<TEST_PASSWORD>` (generated at run time) |
| Invalid emails | `test`, `test@` |
| Required fields of the account form | Password, First name, Last name, Address, State, City, Zipcode, Mobile Number |
| Valid account data | Title `Mrs`, First name `Test`, Last name `User`, Address `123 Test Street`, Country `Singapore`, State `Test`, City `Test`, Zipcode `123456`, Mobile Number `123456789`, Date of birth 1 January 2000 |
| Letters in numeric fields | `test` |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-REG-001 | Signup with a valid name and a new email opens the account form | Positive | High | Yes | The login page (`/login`) is open and the user is not logged in | 1. In **New User Signup**, enter the name `Test User`.<br>2. Enter a unique email.<br>3. Click **Signup**. | The user is taken to `https://automationexercise.com/signup`. The **Enter Account Information** form is displayed with the entered Name and Email already filled in. | FT-06 |
| TC-REG-002 | Signup is blocked when both Name and Email are empty | Negative | Medium | Yes | The login page is open and the user is not logged in | 1. In **New User Signup**, leave Name and Email empty.<br>2. Click **Signup**. | A browser validation bubble appears on the Name field. The form is not submitted and the page stays on `/login`. | FT-06 |
| TC-REG-003 | Signup is blocked when only one of Name or Email is filled (data-driven) | Negative | Medium | Yes | The login page is open and the user is not logged in | 1. Enter only the name `Test User` and leave Email empty, then click **Signup**.<br>2. Reload the page, enter only a unique email and leave Name empty, then click **Signup**. | Step 1: a browser validation bubble appears on the Email field. Step 2: a browser validation bubble appears on the Name field. In both cases the form is not submitted. | FT-06 |
| TC-REG-004 | Signup is blocked for an email in an invalid format (data-driven) | Negative | Medium | Yes | The login page is open and the user is not logged in | 1. Enter the name `Test User`.<br>2. Enter an email from the "Invalid emails" data set.<br>3. Click **Signup**.<br>4. Repeat from a fresh login page for each value in the data set. | A browser validation bubble appears on the Email field. The form is not submitted. | FT-06 |
| TC-REG-005 | Signup with an already registered email shows an error | Negative | High | Yes | An account with `<TEST_EMAIL>` exists and the user is logged out | 1. Open the login page.<br>2. In **New User Signup**, enter the name `Test User`.<br>3. Enter `<TEST_EMAIL>`.<br>4. Click **Signup**. | The error message `Email Address already exist!` is displayed in red below the Email Address field of **New User Signup**. The address bar shows `https://automationexercise.com/signup` and the login and signup page stays displayed. No new account is created. | FT-06 |
| TC-REG-006 | On the account form, Name can be edited and Email cannot | Observation | Low | No | The **Enter Account Information** form is open (see TC-REG-001) | 1. Click the Name field and change the text.<br>2. Click the Email field and try to change the text. | The Name field accepts the change. The Email field does not accept any change. No requirement is available to confirm whether this is intended. | FT-06 |
| TC-REG-007 | Account creation is blocked when a required field is empty (data-driven) | Negative | High | Yes | The **Enter Account Information** form is open (see TC-REG-001) | 1. Fill all fields with the "Valid account data" values.<br>2. Clear one field from the "Required fields" data set.<br>3. Click **Create Account**.<br>4. Repeat from a fresh form for each required field. | A browser validation bubble appears on the empty field. The account is not created and the page stays on `/signup`. | FT-06 |
| TC-REG-008 | Country is set to India when no country is chosen | Observation | Low | No | The **Enter Account Information** form is open (see TC-REG-001) | 1. Do not change the Country field and look at its value. | The Country field shows `India`. No requirement is available to confirm whether this default is intended. | FT-06 |
| TC-REG-009 | Create an account with valid data | Positive | High | Yes | The **Enter Account Information** form is open and Name and Email are filled (see TC-REG-001) | 1. Fill the form with the "Valid account data" values and the password `<TEST_PASSWORD>`.<br>2. Click **Create Account**. | The user is taken to `https://automationexercise.com/account_created`. The **Account Created!** page shows `Congratulations! Your new account has been successfully created!` and `You can now take advantage of member privileges to enhance your online shopping experience with us.`. A **Continue** button is shown. | FT-06 |
| TC-REG-010 | Continue after account creation shows the logged-in state | Positive | High | Yes | The **Account Created!** page is displayed (see TC-REG-009) | 1. Click **Continue**. | The home page `https://automationexercise.com/` is displayed. The header shows **Logout**, **Delete Account** and `Logged in as` followed by the name used at signup. | FT-06 |
| TC-REG-011 | Delete the account; the deleted account can no longer log in | Positive | High | Yes | The user is logged in with an account created for the test | 1. Click **Delete Account** in the header.<br>2. Read the page.<br>3. Click **Continue**.<br>4. Open the login page and log in with the email and password of the deleted account. | The user is taken to `https://automationexercise.com/delete_account`. The **Account Deleted!** page shows `Your account has been permanently deleted!`. After **Continue**, the home page `https://automationexercise.com/` is displayed. The login in step 4 fails: the error message `Your email or password is incorrect!` is displayed in red below the Password field and the user is not logged in. | FT-06 |
| TC-REG-012 | Products added before registration stay in the cart when registering from the checkout prompt | Positive | Medium | Yes | The user is logged out and the cart is empty | 1. Add a product to the cart (for example `Blue Top`).<br>2. Open `/view_cart` and click **Proceed To Checkout**.<br>3. In the popup, click **Register / Login**.<br>4. Register a new account (see TC-REG-001 and TC-REG-009).<br>5. Open `/view_cart`. | The product added in step 1 is still in the cart. | FT-06, FT-05 |
| TC-REG-013 | Password length is not restricted | Observation | Low | No | The **Enter Account Information** form is open (see TC-REG-001) | 1. Enter a password of 1 character and complete the form with valid data, then click **Create Account**.<br>2. Repeat with a new unique email and a password of 100 characters. | In both cases the account is created. No requirement is available to confirm whether there should be a password length rule. | FT-06 |
| TC-REG-014 | Zipcode and Mobile Number must not accept letters (data-driven) | Negative | Medium | No (Defect candidate) | The **Enter Account Information** form is open (see TC-REG-001) | 1. Fill the form with the "Valid account data" values.<br>2. Replace the Zipcode with `test` and click **Create Account**.<br>3. Repeat from a fresh form with the Mobile Number set to `test` and a valid Zipcode. | **Expected:** the account is not created and a validation message is shown for the field.<br>**Observed:** the account is created and the **Account Created!** page is displayed. | FT-06 |
| TC-REG-015 | A date of birth that does not exist must not be accepted | Boundary | Medium | No (Defect candidate) | The **Enter Account Information** form is open (see TC-REG-001) | 1. Fill the form with the "Valid account data" values.<br>2. Select Day `31` and Month `February` for the date of birth.<br>3. Click **Create Account**. | **Expected:** the account is not created and a validation message is shown for the date of birth.<br>**Observed:** the account is created and the **Account Created!** page is displayed. | FT-06 |
