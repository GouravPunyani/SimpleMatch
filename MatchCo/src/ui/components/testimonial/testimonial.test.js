// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { Testimonial as Comp } from './testimonial'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
