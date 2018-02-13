// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { StaticLayout as Comp } from './static-layout'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
