// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { DashboardCarousel as Comp } from './dashboard-carousel'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
