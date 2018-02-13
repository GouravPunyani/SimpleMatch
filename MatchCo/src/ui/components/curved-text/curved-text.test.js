// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { CurvedText as Comp } from './curved-text'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
