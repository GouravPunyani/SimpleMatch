// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ContactMethod as Comp } from './contact-method'

test('renders correctly', () => {
  const tree = renderer.create(<Comp text='a' icon='b' onPress={() => null} />)
  expect(tree).toMatchSnapshot()
})
