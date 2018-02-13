// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { Text as Comp } from './text'

test('renders correctly', () => {
  const tree = renderer.create(<Comp text='hi' />)
  expect(tree).toMatchSnapshot()
})
