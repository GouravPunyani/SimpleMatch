// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ProductTabBar as Comp } from './product-tab-bar'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
