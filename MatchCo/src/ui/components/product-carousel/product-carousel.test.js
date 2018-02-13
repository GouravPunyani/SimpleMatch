// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ProductCarousel as Comp } from './product-carousel'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
