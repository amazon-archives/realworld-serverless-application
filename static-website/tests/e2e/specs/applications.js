/* eslint-disable no-param-reassign */
// For authoring Nightwatch tests, see
// http://nightwatchjs.org/guide#usage
const uuid = require('uuid');
const { createTestUser } = require('../utils/cognitoAdmin');

const generateTestData = () => {
  const id = uuid.v4();
  return {
    name: `testName-${id}`,
    author: `testAuthor-${id}`,
    homePage: `http://test.com/${id}`,
    description: `testDescription-${id}`,
  };
};

module.exports = {
  before(browser, done) {
    browser.globals.testData = { app: generateTestData() };

    createTestUser().then((testUser) => {
      browser.globals.testUser = testUser;
      done();
    });
  },
  beforeEach: (browser) => {
    // Sign in before each test
    const { username, password } = browser.globals.testUser;
    browser.url(browser.launchUrl);

    browser.page.welcome()
      .click('@signInBtn');

    browser.page.signIn()
      .setValue('@usernameInput', username)
      .setValue('@passwordInput', password)
      .click('@signInBtn');

    browser.assert.urlEquals(`${browser.launchUrl}/#/applications`, 'Should redirect to My applications page after sign in');
  },
  afterEach: (browser) => {
    browser.end();
  },
  'Publish application': (browser) => {
    browser.url(browser.launchUrl);
    browser.page.welcome()
      .click('@tryDemoBtn');

    browser.page.myApps()
      .click('@publishAppBtn');

    const testApp = browser.globals.testData.app;

    browser.page.publishApp()
      .setValue('@appNameInput', testApp.name)
      .setValue('@authorInput', testApp.author)
      .setValue('@homePageUrlInput', testApp.homePage)
      .setValue('@descriptionInput', testApp.description)
      .click('@publishBtn')
      .waitForElementNotPresent('@publishBtn');

    browser.assert.urlEquals(`${browser.launchUrl}/#/applications/${testApp.name}`, 'Should redirect to the App details page');
  },
  'Application details': (browser) => {
    const testApp = browser.globals.testData.app;
    browser.url(`${browser.launchUrl}/#/applications/${testApp.name}`);

    browser.page.appDetails().assert.containsText('@details', testApp.name);
    browser.page.appDetails().assert.containsText('@details', testApp.author);
    browser.page.appDetails().assert.containsText('@details', testApp.homePage);
    browser.page.appDetails().assert.containsText('@details', testApp.description);
  },
  'My applications': (browser) => {
    const testApp = browser.globals.testData.app;
    browser.url(`${browser.launchUrl}/#/applications`);

    browser.page.myApps().assert.containsText('@appsTable', testApp.name);
    browser.page.myApps().assert.containsText('@appsTable', testApp.description);
  },
  'Edit application': (browser) => {
    const testApp = browser.globals.testData.app;

    browser.url(`${browser.launchUrl}/#/applications/${testApp.name}/edit`);

    const modifiedAuthor = 'modifiedAuthor';
    const modifiedHomePage = 'http://modified-page.com';
    const modifiedDescription = 'modifiedDescription';

    browser.page.editApp()
      .pause(2000)
      .clearValue('@authorInput')
      .setValue('@authorInput', modifiedAuthor)
      .clearValue('@homePageUrlInput')
      .setValue('@homePageUrlInput', modifiedHomePage)
      .clearValue('@descriptionInput')
      .setValue('@descriptionInput', modifiedDescription)
      .click('@saveBtn')
      .waitForElementNotPresent('@saveBtn');

    browser.assert.urlEquals(`${browser.launchUrl}/#/applications/${testApp.name}`, 'Should redirect to the App details page');
    browser.page.appDetails().assert.containsText('@details', modifiedAuthor);
    browser.page.appDetails().assert.containsText('@details', modifiedHomePage);
    browser.page.appDetails().assert.containsText('@details', modifiedDescription);
  },
  'Delete application': (browser) => {
    const testApp = browser.globals.testData.app;

    browser.url(`${browser.launchUrl}/#/applications/${testApp.name}`);

    browser.page.appDetails()
      .waitForElementVisible('@deleteBtn')
      .click('@deleteBtn')
      .waitForElementVisible('@deleteModal')
      .click('@confirmDeleteBtn')
      .waitForElementNotPresent('@confirmDeleteBtn');

    browser.assert.urlEquals(`${browser.launchUrl}/#/applications`, 'Should redirect to My applications page');
    browser.page.myApps().expect.element('@appsTable').text.to.not.contain(testApp.name, 'Deleted app should no longer be listed on My applications');
  },
};
