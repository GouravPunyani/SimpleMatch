// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { KeyboardSpacer as Comp } from './keyboard-spacer'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
