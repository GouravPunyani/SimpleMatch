// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { Button as Comp } from './button'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
