// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ProductCarouselFooter as Comp } from './product-carousel-footer'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
