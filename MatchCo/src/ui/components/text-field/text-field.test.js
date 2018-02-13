// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { TextField as Comp } from './text-field'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
