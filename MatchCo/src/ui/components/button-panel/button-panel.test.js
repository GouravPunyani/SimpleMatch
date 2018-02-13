// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import Comp from './button-panel'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
