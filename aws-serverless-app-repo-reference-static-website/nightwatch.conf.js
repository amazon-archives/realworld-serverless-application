const launchUrl = process.env.WEBSITE_URL || 'http://localhost:8080';

module.exports = {
  src_folders: ['tests/e2e/specs'],
  output_folder: 'tests/e2e/reports',
  page_objects_path: 'tests/e2e/page-objects',
  test_settings: {
    chrome: {
      launch_url: launchUrl,
      webdriver: {
        start_process: true,
        server_path: './node_modules/.bin/chromedriver',
        port: 9515,
      },
      desiredCapabilities: {
        browserName: 'chrome',
        chromeOptions: {
          w3c: false,
          args: [
            'headless',
          ],
        },
      },
    },
    firefox: {
      launch_url: launchUrl,
      webdriver: {
        start_process: true,
        server_path: './node_modules/.bin/geckodriver',
        cli_args: [
          '--log', 'debug',
        ],
        port: 4444,
      },
      desiredCapabilities: {
        alwaysMatch: {
          browserName: 'firefox',
          'moz:firefoxOptions': {
            args: ['--headless'],
          },
        },
      },
    },
  },
};
