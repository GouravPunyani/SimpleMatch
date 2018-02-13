// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { BottleImage as Comp } from './bottle-image'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
