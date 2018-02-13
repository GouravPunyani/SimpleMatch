// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ContactSocialButton as Comp } from './contact-social-button'

test('renders correctly', () => {
  const tree = renderer.create(
    <Comp network='facebook' onPress={() => null} />
  )
  expect(tree).toMatchSnapshot()
})
