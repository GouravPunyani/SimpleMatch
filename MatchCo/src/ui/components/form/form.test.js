// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { Form as Comp } from './form'

test('renders correctly', () => {
  const tree = renderer.create(<Comp />)
  expect(tree).toMatchSnapshot()
})
