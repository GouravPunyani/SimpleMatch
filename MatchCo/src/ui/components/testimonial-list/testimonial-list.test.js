// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { TestimonialList as Comp } from './testimonial-list'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
