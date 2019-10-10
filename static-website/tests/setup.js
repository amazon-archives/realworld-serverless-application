/* eslint no-undef: 0 */

// common mocha setup

import chai from 'chai';
import sinon from 'sinon';
import sinonChai from 'sinon-chai';

// support sinon-chai assertions
chai.use(sinonChai);

afterEach(() => {
  sinon.restore();
});
