# Test Cases: Contact Us (FT-09)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-09 - Contact us |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- **Auto** shows whether the case is a candidate for Selenium automation.
- Only fake data is used. Messages are sent to a public practice site, so the text never contains real personal data.
- Messages for empty required fields and for badly formatted emails are browser validation bubbles, not messages of the website. The tests check that a bubble appears on the right field and that the form is not submitted, not the text.
- After **Submit**, the browser shows a confirmation dialog (`Press OK to proceed!`). It is a dialog of the browser and not a part of the web page, so the tests handle it as an alert.
- Files to attach are created by the test at run time and deleted after the test. They contain no real information.
- The Contact us page can be opened by a logged-out user, so each case starts with the user logged out and the page `https://automationexercise.com/contact_us` open unless the case says otherwise.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Value |
|---|---|
| Name | `Test User` |
| Email | `test@example.com` |
| Subject | `Test subject` |
| Message | `Test message` |
| Invalid emails | `test`, `test@` |
| Files | A small `.txt` file; a `.jpg` image of about 5 MB; a `.txt` file of 20 MB |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-CONTACT-001 | Submit is blocked when all fields are empty | Negative | Medium | Yes | The Contact us page is open | 1. Leave all fields empty.<br>2. Click **Submit**. | A browser validation bubble appears on the Email field. The form is not submitted. | FT-09 |
| TC-CONTACT-002 | Submit is blocked for an email in an invalid format (data-driven) | Negative | Medium | Yes | The Contact us page is open | 1. Fill Name, Subject and Message with the data set values.<br>2. Enter a value from the "Invalid emails" data set in Email.<br>3. Click **Submit**.<br>4. Repeat from a fresh page for each value. | A browser validation bubble appears on the Email field. The form is not submitted. | FT-09 |
| TC-CONTACT-003 | A message with all fields filled shows a confirmation dialog and then a success message | Positive | High | Yes | The Contact us page is open | 1. Fill Name, Email, Subject and Message with the data set values.<br>2. Do not attach a file.<br>3. Click **Submit**.<br>4. Click **OK** in the dialog. | After step 3 a browser dialog with the title `automationexercise.com` and the text `Press OK to proceed!` is displayed, with the buttons **OK** and **Cancel**. After step 4 a message in a green box `Success! Your details have been submitted successfully.` is displayed with a **Home** button. | FT-09 |
| TC-CONTACT-004 | Cancel in the confirmation dialog keeps the form open | Positive | Medium | Yes | The Contact us page is open with all fields filled | 1. Click **Submit**.<br>2. Click **Cancel** in the dialog. | The dialog closes and the Contact us form stays open. No success message is displayed. | FT-09 |
| TC-CONTACT-005 | The Home button after a successful message opens the home page | Positive | Low | Yes | The success message is displayed (see TC-CONTACT-003) | 1. Click **Home**. | The home page `https://automationexercise.com/` is displayed. | FT-09 |
| TC-CONTACT-006 | A message with a small text file attached is sent | Positive | Medium | Yes | The Contact us page is open | 1. Fill Name, Email, Subject and Message.<br>2. Choose the small `.txt` file.<br>3. Click **Submit** and click **OK** in the dialog. | After step 2 the file name and its type are shown next to the file button. After step 3 the result is the same as in TC-CONTACT-003. | FT-09 |
| TC-CONTACT-007 | A message with only the Email filled can be sent | Observation | Medium | No | The Contact us page is open | 1. Enter only the Email `test@example.com` and leave Name, Subject and Message empty.<br>2. Click **Submit** and click **OK** in the dialog. | The message is sent and the success message is displayed. Only the Email field is required. No requirement is available to confirm whether the other fields should be required. | FT-09 |
| TC-CONTACT-008 | Larger files and images are accepted (data-driven) | Boundary | Low | No | The Contact us page is open | 1. Fill Name, Email, Subject and Message.<br>2. Choose a file from the "Files" data set (the `.jpg` image of about 5 MB, then the `.txt` file of 20 MB).<br>3. Click **Submit** and click **OK** in the dialog.<br>4. Repeat for each file. | Each file is accepted and the success message is displayed. No size limit was seen up to 20 MB. Larger files were not tried. This case is checked by hand because large files make automated runs slow. | FT-09 |
