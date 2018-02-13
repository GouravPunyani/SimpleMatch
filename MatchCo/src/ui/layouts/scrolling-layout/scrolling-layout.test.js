// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ScrollingLayout as Comp } from './scrolling-layout'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
