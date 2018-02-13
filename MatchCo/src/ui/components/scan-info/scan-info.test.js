// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ScanInfo as Comp } from './scan-info'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
